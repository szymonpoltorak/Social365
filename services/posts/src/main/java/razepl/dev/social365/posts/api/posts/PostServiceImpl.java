package razepl.dev.social365.posts.api.posts;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.cassandra.core.query.CassandraPageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import razepl.dev.social365.posts.api.posts.data.DataPage;
import razepl.dev.social365.posts.api.posts.data.PostResponse;
import razepl.dev.social365.posts.api.posts.interfaces.PostData;
import razepl.dev.social365.posts.api.posts.interfaces.PostService;
import razepl.dev.social365.posts.clients.profile.ProfileService;
import razepl.dev.social365.posts.entities.comment.interfaces.CommentRepository;
import razepl.dev.social365.posts.entities.post.Post;
import razepl.dev.social365.posts.entities.post.PostKey;
import razepl.dev.social365.posts.entities.post.interfaces.PostMapper;
import razepl.dev.social365.posts.entities.post.interfaces.PostRepository;
import razepl.dev.social365.posts.utils.exceptions.PostDoesNotExistException;
import razepl.dev.social365.posts.utils.exceptions.UserIsNotAuthorException;
import razepl.dev.social365.posts.utils.validators.interfaces.PostValidator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final ProfileService profileService;
    private final PostValidator postValidator;
    private final CommentRepository commentRepository;

    @Override
    public final int getUsersPostCount(String profileId) {
        log.info("Getting post count for profile with id: {}", profileId);

        return postRepository.countAllByAuthorId(profileId);
    }

    @Override
    public DataPage<PostData> getPostsOnPage(String profileId, int pageNumber, int pageSize) {
        int currentPageNumber = pageNumber;
        int currentPageSize = pageSize;
        boolean hasNext = false;
        Pageable pageable = CassandraPageRequest.of(0, pageSize);
        Collection<Post> result = new ArrayList<>(currentPageSize + 1);
        List<String> friendsIds = new ArrayList<>(profileService.getFriendsIds(profileId, currentPageNumber).toList());

        while (true) {
            log.info("Found {} friends for profile with id: {}", friendsIds.size(), profileId);

            if (friendsIds.isEmpty()) {
                return new DataPage<>(List.of(), currentPageNumber, pageSize, false);
            }
            friendsIds.add(profileId);

            Slice<Post> posts = postRepository.findAllByFollowedUserIdsOrProfileId(friendsIds, pageable);

            log.info("Found {} posts for profile with id: {}", posts.getNumberOfElements(), profileId);

            //TODO: this will not work for sure for more users but for now i cannot test it (it is when page number is bigger than 0)
            if (pageNumber > 0) {
                int i = pageNumber - 1;
                pageable = posts.nextPageable();

                while (i-- > 0) {
                    pageable = pageable.next();
                }
                continue;
            }
            result.addAll(posts.getContent());

            if (result.size() >= currentPageSize || !posts.hasNext()) {
                hasNext = posts.hasNext();

                break;
            }
            friendsIds = new ArrayList<>(profileService.getFriendsIds(profileId, ++currentPageNumber).toList());
        }
        log.info("Found posts returning result...");

        List<PostData> slice = result
                .parallelStream()
                .map(post -> {
                    if (post.isSharedPost()) {
                        return postMapper.toSharedPostResponse(post, profileId);
                    }
                    return postMapper.toPostResponse(post, profileId);
                })
                .toList();

        return new DataPage<>(slice, currentPageNumber, pageSize, hasNext);
    }

    @Override
    public PostData createPost(String profileId, String content, boolean hasAttachments) {
        log.info("Creating post for profileId: {}, with content: {}", profileId, content);

        postValidator.validatePostContent(content);

        Post post = Post
                .builder()
                .key(PostKey
                        .builder()
                        .authorId(profileId)
                        .postId(UUID.randomUUID())
                        .creationDateTime(LocalDateTime.now())
                        .build()
                )
                .content(content)
                .hasAttachments(hasAttachments)
                .build();

        Post savedPost = postRepository.save(post);

        log.info("Post with id: {} created for profile with id: {}", savedPost.getPostId(), profileId);

        return postMapper.toPostResponseNoImages(savedPost, profileId);
    }

    @Override
    public PostData editPost(String profileId, String postId, String content) {
        log.info("Editing post with id: {} for profileId: {}, with content: {}", postId, profileId, content);

        postValidator.validatePostContent(content);

        Post post = getPostFromRepository(postId);

        if (!post.getAuthorId().equals(profileId)) {
            throw new UserIsNotAuthorException(profileId);
        }
        post.setContent(content);

        Post savedPost = postRepository.save(post);

        log.info("Post with id: {} edited for profile with id: {}", savedPost.getPostId(), profileId);

        return postMapper.toPostResponse(savedPost, profileId);
    }

    @Override
    public PostData updateLikePostCount(String profileId, String postId) {
        log.info("Updating like count for post with id: {} for profileId: {}", postId, profileId);

        Post post = getPostFromRepository(postId);

        if (post.isLikedBy(profileId)) {
            post.getUserLikedIds().remove(profileId);
        } else {
            post.getUserLikedIds().add(profileId);
        }
        Post savedPost = postRepository.save(post);

        log.info("Post with id: {} like count updated for profile with id: {}", savedPost.getPostId(), profileId);

        return postMapper.toPostResponse(savedPost, profileId);
    }

    @Override
    public PostData updateNotificationStatus(String profileId, String postId) {
        log.info("Updating notification status for post with id: {} for profileId: {}", postId, profileId);

        Post post = getPostFromRepository(postId);

        if (post.areNotificationsTurnedOnBy(profileId)) {
            post.getUserNotificationIds().remove(profileId);
        } else {
            post.getUserNotificationIds().add(profileId);
        }
        Post savedPost = postRepository.save(post);

        log.info("Post with id: {} notification status updated for profile with id: {}", savedPost.getPostId(), profileId);

        return postMapper.toPostResponse(savedPost, profileId);
    }

    @Override
    public PostData updateBookmarkStatus(String profileId, String postId) {
        log.info("Updating bookmark status for post with id: {} for profileId: {}", postId, profileId);

        Post post = getPostFromRepository(postId);

        if (post.isBookmarkedBy(profileId)) {
            post.getBookmarkedUserIds().remove(profileId);
        } else {
            post.getBookmarkedUserIds().add(profileId);
        }
        Post savedPost = postRepository.save(post);

        log.info("Post with id: {} bookmark status updated for profile with id: {}", savedPost.getPostId(), profileId);

        return postMapper.toPostResponse(savedPost, profileId);
    }

    @Override
    public PostData sharePost(String profileId, String postId, String content) {
        log.info("Updating shares count for post with id: {} for profileId: {}", postId, profileId);

        Post post = getPostFromRepository(postId);

        Post sharedPost = Post
                .builder()
                .key(PostKey
                        .builder()
                        .authorId(profileId)
                        .postId(UUID.randomUUID())
                        .creationDateTime(LocalDateTime.now())
                        .build()
                )
                .content(content)
                .originalPostId(post.getPostId())
                .hasAttachments(false)
                .userLikedIds(Set.of())
                .userSharedIds(Set.of())
                .userNotificationIds(Set.of())
                .bookmarkedUserIds(Set.of())
                .build();

        post.sharePostByProfile(profileId);

        Post savedPost = postRepository.save(post);

        log.info("Post with id: {} shares count updated for profile with id: {}", savedPost.getPostId(), profileId);

        Post savedSharedPost = postRepository.save(sharedPost);

        log.info("Post with id: {} shared for profile with id: {}", savedSharedPost.getPostId(), profileId);

        return postMapper.toPostResponse(savedPost, profileId);
    }

    @Override
    @Transactional
    public PostResponse deletePost(String profileId, String postId) {
        log.info("Deleting post with id: {} for profileId: {}", postId, profileId);

        Post post = getPostFromRepository(postId);

        if (!post.getAuthorId().equals(profileId)) {
            throw new UserIsNotAuthorException(profileId);
        }
        log.info("Deleting post with id: {}", post.getPostId());

        postRepository.deleteById(post.getPostId());

        log.info("Deleting all comments connected with post with id: {}", post.getPostId());

        commentRepository.deleteAllByPostId(post.getPostId());

        return PostResponse.builder().build();
    }

    private Post getPostFromRepository(String postId) {
        Post post = postRepository.findById(UUID.fromString(postId))
                .orElseThrow(() -> new PostDoesNotExistException(postId));

        log.info("Found post with id: {}", post);

        return post;
    }

}
