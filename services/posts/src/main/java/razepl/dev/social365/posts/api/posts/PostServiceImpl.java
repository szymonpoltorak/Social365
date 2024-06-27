package razepl.dev.social365.posts.api.posts;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.cassandra.core.query.CassandraPageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
import razepl.dev.social365.posts.utils.pagination.data.PageInfo;
import razepl.dev.social365.posts.utils.pagination.data.PostsCassandraPage;
import razepl.dev.social365.posts.utils.pagination.interfaces.CassandraPage;
import razepl.dev.social365.posts.utils.pagination.interfaces.PagingState;
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
    public CassandraPage<PostData> getPostsOnPage(String profileId, PageInfo pageInfo) {
        log.info("Getting posts for profile with id: {}, using page info : {}", profileId, pageInfo);

        int currentFriendPage = pageInfo.friendPageNumber();
        int pageSize = pageInfo.pageSize();
        Pageable pageable = pageInfo.toPageable();
        Collection<Post> result = new ArrayList<>(pageSize + 1);

        while (true) {
            Page<String> friendsPage = profileService.getFriendsIds(profileId, currentFriendPage);
            List<String> friendsIds = new ArrayList<>(profileService.getFriendsIds(profileId, currentFriendPage).toList());

            if (friendsIds.isEmpty()) {
                log.info("No friends found for profile with id: {} on page {}", profileId, currentFriendPage);

                return createCassandraPage(pageable, result, profileId, currentFriendPage);
            }
            Slice<Post> posts = postRepository.findAllByFollowedUserIdsOrProfileId(friendsIds, pageable);

            log.info("Found {} posts for profile with id: {}", posts.getNumberOfElements(), profileId);

            result.addAll(posts.getContent());

            int resultSize = result.size();

            if (resultSize >= pageSize) {
                log.info("Found enough posts returning result of size: {}", resultSize);

                return createCassandraPage(getNextPageable(posts), result, profileId, currentFriendPage);
            }
            if (!friendsPage.hasNext()) {
                log.info("There are no more friends to fetch posts from returning result of size: {}", resultSize);

                return createCassandraPage(getNextPageable(posts), result, profileId, currentFriendPage);
            }
            currentFriendPage++;
        }
    }

    private PostsCassandraPage<PostData> createCassandraPage(Pageable nextPageable, Collection<Post> result,
                                                             String profileId, int currentFriendsPage) {
        log.info("Found posts returning result...");

        CassandraPageRequest pageable = (CassandraPageRequest) nextPageable;
        PagingState pagingState = PagingState.newInstance(pageable.getPagingState());

        List<PostData> content = result
                .parallelStream()
                .map(post -> {
                    if (post.isSharedPost()) {
                        return postMapper.toSharedPostResponse(post, profileId);
                    }
                    return postMapper.toPostResponse(post, profileId);
                })
                .toList();

        return new PostsCassandraPage<>(content, currentFriendsPage, pageable.getPageSize(),
                pageable.hasNext(), PagingState.encode(pagingState));
    }

    private <T> Pageable getNextPageable(Slice<T> data) {
        return data.hasNext() ? data.nextPageable() : data.getPageable();
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
    public PostData deletePost(String profileId, String postId) {
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
