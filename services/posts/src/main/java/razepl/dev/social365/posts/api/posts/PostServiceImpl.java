package razepl.dev.social365.posts.api.posts;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import razepl.dev.social365.posts.api.posts.data.EditPostRequest;
import razepl.dev.social365.posts.api.posts.data.PostResponse;
import razepl.dev.social365.posts.api.posts.interfaces.PostData;
import razepl.dev.social365.posts.api.posts.interfaces.PostService;
import razepl.dev.social365.posts.clients.images.ImageService;
import razepl.dev.social365.posts.clients.profile.ProfileService;
import razepl.dev.social365.posts.config.User;
import razepl.dev.social365.posts.entities.comment.Comment;
import razepl.dev.social365.posts.entities.comment.interfaces.CommentRepository;
import razepl.dev.social365.posts.entities.comment.reply.intefaces.ReplyCommentRepository;
import razepl.dev.social365.posts.entities.post.Post;
import razepl.dev.social365.posts.entities.post.PostKey;
import razepl.dev.social365.posts.entities.post.interfaces.PostMapper;
import razepl.dev.social365.posts.entities.post.interfaces.PostRepository;
import razepl.dev.social365.posts.utils.exceptions.PostDoesNotExistException;
import razepl.dev.social365.posts.utils.exceptions.UserIsNotAuthorException;
import razepl.dev.social365.posts.utils.pagination.data.PageInfo;
import razepl.dev.social365.posts.utils.pagination.data.PostsCassandraPage;
import razepl.dev.social365.posts.utils.pagination.data.ProfileSocialPage;
import razepl.dev.social365.posts.utils.pagination.interfaces.SocialPage;
import razepl.dev.social365.posts.utils.validators.interfaces.PostValidator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private static final int NOT_NEEDED = -1;

    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final ProfileService profileService;
    private final PostValidator postValidator;
    private final CommentRepository commentRepository;
    private final ReplyCommentRepository replyCommentRepository;
    private final ImageService imageService;

    @Override
    public SocialPage<PostData> getPostsOnPage(String profileId, PageInfo pageInfo) {
        log.info("Getting posts for profile with id: {}, using page info : {}", profileId, pageInfo);

        int currentFriendPage = pageInfo.friendPageNumber();
        int pageSize = pageInfo.pageSize();
        Pageable pageable = pageInfo.toPageable();
        Collection<Post> result = new ArrayList<>(pageSize + 1);

        while (true) {
            ProfileSocialPage<String> friendsPage = profileService.getFriendsIds(profileId, currentFriendPage);
            List<String> friendsIds = friendsPage.data();

            if (friendsIds.isEmpty()) {
                log.info("No friends found for profile with id: {} on page {}", profileId, currentFriendPage);

                return PostsCassandraPage.of(pageable, result, post -> postMapper.toPostData(post, profileId), currentFriendPage);
            }
            friendsIds.add(profileId);

            Slice<Post> posts = postRepository.findAllByFollowedUserIdsOrProfileId(friendsIds, pageable);

            log.info("Found {} posts for profile with id: {}", posts.getNumberOfElements(), profileId);

            result.addAll(posts.getContent());

            int resultSize = result.size();

            if (resultSize >= pageSize) {
                log.info("Found enough posts returning result of size: {}", resultSize);

                return PostsCassandraPage.of(posts.getPageable(), result, post -> postMapper.toPostData(post, profileId), currentFriendPage);
            }
            if (!friendsPage.hasNextPage()) {
                log.info("There are no more friends to fetch posts from returning result of size: {}", resultSize);

                return PostsCassandraPage.of(posts.getPageable(), result, post -> postMapper.toPostData(post, profileId), currentFriendPage);
            }
            currentFriendPage++;
        }
    }

    @Override
    public final SocialPage<PostData> getUsersPosts(String profileId, PageInfo pageInfo) {
        log.info("Getting user posts with id: {}, using page info : {}", profileId, pageInfo);

        Pageable pageable = pageInfo.toPageable();

        Slice<Post> posts = postRepository.findAllByAuthorId(profileId, pageable);

        log.info("Found total posts : {}", posts.getNumberOfElements());

        return PostsCassandraPage.of(posts.getPageable(), posts.getContent(), post -> postMapper.toPostData(post, profileId), NOT_NEEDED);
    }

    @Override
    public final int getUsersPostCount(String profileId) {
        log.info("Getting post count for profile with id: {}", profileId);

        return postRepository.countAllByAuthorId(profileId);
    }

    @Override
    public PostData createPost(String profileId, String content, boolean hasAttachments) {
        log.info("Creating post for profileId: {}, with content: {}", profileId, content);

        postValidator.validatePostContent(content);

        String creationDateTime = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        Post post = Post
                .builder()
                .key(PostKey.of(profileId, UUID.randomUUID(), creationDateTime))
                .content(content == null ? "" : content)
                .hasAttachments(hasAttachments)
                .build();

        Post savedPost = postRepository.save(post);

        log.info("Post with id: {} created for profile with id: {}", savedPost.getPostId(), profileId);

        return postMapper.toPostResponseNoImages(savedPost, profileId);
    }

    @Override
    public PostData editPost(User user, EditPostRequest editPostRequest) {
        log.info("Editing post with request: {}", editPostRequest);

        String profileId = user.profileId();
        String content = editPostRequest.content();

        postValidator.validatePostContent(content);

        Post post = getPostFromRepository(profileId, editPostRequest.postId());

        if (!post.isAuthorId(profileId)) {
            throw new UserIsNotAuthorException(profileId);
        }
        post.setContent(content);
        post.setHasAttachments(editPostRequest.hasAttachments());

        Post savedPost = postRepository.save(post);

        log.info("Post with id: {} edited for profile with id: {}", savedPost.getPostId(), profileId);

        return postMapper.toPostResponse(savedPost, profileId);
    }

    @Override
    public PostData updateLikePostCount(String profileId, String postId, String authorId) {
        log.info("Updating like count for post with id: {} for profileId: {}", postId, profileId);

        Post post = getPostFromRepository(authorId, postId);

        if (post.isLikedBy(profileId)) {
            post.getUserLikedIds().remove(profileId);
        } else {
            post.addUserLikedId(profileId);
        }
        Post savedPost = postRepository.save(post);

        log.info("Post with id: {} like count updated for profile with id: {}", savedPost.getPostId(), profileId);

        return postMapper.toPostResponse(savedPost, profileId);
    }

    @Override
    public PostData updateNotificationStatus(String profileId, String postId, String authorId) {
        log.info("Updating notification status for post with id: {} for profileId: {}", postId, profileId);

        Post post = getPostFromRepository(authorId, postId);

        if (post.areNotificationsTurnedOnBy(profileId)) {
            post.getUserNotificationIds().remove(profileId);
        } else {
            post.addUserNotificationId(profileId);
        }
        Post savedPost = postRepository.save(post);

        log.info("Post with id: {} notification status updated for profile with id: {}", savedPost.getPostId(), profileId);

        return postMapper.toPostResponse(savedPost, profileId);
    }

    @Override
    public PostData updateBookmarkStatus(String profileId, String postId, String authorId) {
        log.info("Updating bookmark status for post with id: {} for profileId: {}", postId, profileId);

        Post post = getPostFromRepository(authorId, postId);

        if (post.isBookmarkedBy(profileId)) {
            post.getBookmarkedUserIds().remove(profileId);
        } else {
            post.addBookmarkedUserId(profileId);
        }
        Post savedPost = postRepository.save(post);

        log.info("Post with id: {} bookmark status updated for profile with id: {}", savedPost.getPostId(), profileId);

        return postMapper.toPostResponse(savedPost, profileId);
    }

    @Override
    public PostData sharePost(String profileId, String postId, String content, String authorId) {
        log.info("Updating shares count for post with id: {} for profileId: {}", postId, profileId);

        Post post = getPostFromRepository(authorId, postId);
        String shareCreationDateTime = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        Post sharedPost = Post
                .builder()
                .key(PostKey.of(profileId, UUID.randomUUID(), shareCreationDateTime))
                .content(content)
                .originalPostId(post.getPostId())
                .originalPostAuthorId(authorId)
                .hasAttachments(false)
                .build();

        post.sharePostByProfile(profileId);

        Post savedPost = postRepository.save(post);

        log.info("Post with id: {} shares count updated for profile with id: {}", savedPost.getPostId(), profileId);

        Post savedSharedPost = postRepository.save(sharedPost);

        log.info("Saved shared post : {}", savedSharedPost);

        return postMapper.toSharedPostResponse(savedSharedPost, savedPost, profileId);
    }

    @Override
    @Transactional
    public PostData deletePost(String profileId, String postId, String authorId) {
        log.info("Deleting post with id: {} for profileId: {}", postId, profileId);

        Post post = getPostFromRepository(authorId, postId);

        if (!post.isAuthorId(profileId)) {
            throw new UserIsNotAuthorException(profileId);
        }
        log.info("Deleting all comments connected with post with id: {}", post.getPostId());

        List<Comment> comments = commentRepository.findAllByPostId(post.getPostId());

        commentRepository.deleteAllByPostId(post.getPostId());

        log.info("Deleting all images connected with post with id: {}", post.getPostId());

        comments
                .parallelStream()
                .forEach(comment -> imageService.deleteCommentImageById(comment.getCommentId().toString()));

        log.info("Deleting all replies connected with post with id: {}", post.getPostId());

        comments
                .parallelStream()
                .forEach(comment -> replyCommentRepository.deleteAllByCommentId(comment.getCommentId()));

        log.info("Deleting post with id: {}", post.getKey());

        postRepository.deleteByPostId(post.getPostId(), post.getCreationDateTime(), post.getAuthorId());

        imageService.deleteImagesByPostId(post.getPostId().toString());

        return PostResponse.builder().build();
    }

    private Post getPostFromRepository(String authorId, String postId) {
        Post post = postRepository.findByPostId(authorId, UUID.fromString(postId))
                .orElseThrow(() -> new PostDoesNotExistException(postId, authorId));

        log.info("Found post with id: {}", post);

        return post;
    }

}
