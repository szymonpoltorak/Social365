package razepl.dev.social365.posts.api.posts;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import razepl.dev.social365.posts.api.posts.data.PostResponse;
import razepl.dev.social365.posts.api.posts.interfaces.PostService;
import razepl.dev.social365.posts.clients.profile.ProfileService;
import razepl.dev.social365.posts.entities.comment.interfaces.CommentRepository;
import razepl.dev.social365.posts.entities.post.Post;
import razepl.dev.social365.posts.entities.post.interfaces.PostMapper;
import razepl.dev.social365.posts.entities.post.interfaces.PostRepository;
import razepl.dev.social365.posts.utils.exceptions.PostDoesNotExistException;
import razepl.dev.social365.posts.utils.exceptions.UserIsNotAuthorException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final ProfileService profileService;
    private final CommentRepository commentRepository;

    @Override
    public Page<PostResponse> getPostsOnPage(String profileId, Pageable pageable) {

        log.info("Getting posts for profileId: {}, with pageable : {}", profileId, pageable);

        List<String> friendsIds = profileService.getFriendsIds(profileId);

        log.info("Found {} friends for profile with id: {}", friendsIds.size(), profileId);

        Page<Post> posts = postRepository.findAllByFollowedUserIds(friendsIds, pageable);

        log.info("Found {} posts for profile with id: {}", posts.getTotalElements(), profileId);

        return posts.map(post -> postMapper.toPostResponse(post, profileId));
    }

    //TODO: Implement handling images
    @Override
    public PostResponse createPost(String profileId, String content) {
        log.info("Creating post for profileId: {}, with content: {}", profileId, content);

        Post post = Post
                .builder()
                .authorId(profileId)
                .content(content)
                .creationDateTime(LocalDateTime.now())
                .build();

        Post savedPost = postRepository.save(post);

        log.info("Post with id: {} created for profile with id: {}", savedPost.getPostId(), profileId);

        return postMapper.toPostResponse(savedPost, profileId);
    }

    @Override
    public PostResponse editPost(String profileId, String postId, String content) {
        log.info("Editing post with id: {} for profileId: {}, with content: {}", postId, profileId, content);

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
    public PostResponse updateLikePostCount(String profileId, String postId) {
        log.info("Updating like count for post with id: {} for profileId: {}", postId, profileId);

        Post post = getPostFromRepository(postId);

        if (post.isPostLikedBy(profileId)) {
            post.getUserLikedIds().remove(profileId);
        } else {
            post.getUserLikedIds().add(profileId);
        }
        Post savedPost = postRepository.save(post);

        log.info("Post with id: {} like count updated for profile with id: {}", savedPost.getPostId(), profileId);

        return postMapper.toPostResponse(savedPost, profileId);
    }

    @Override
    public PostResponse updateNotificationStatus(String profileId, String postId) {
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
    public PostResponse updateBookmarkStatus(String profileId, String postId) {
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
    public PostResponse updateSharesCount(String profileId, String postId) {
        log.info("Updating shares count for post with id: {} for profileId: {}", postId, profileId);

        Post post = getPostFromRepository(postId);

        post.sharePostByProfile(profileId);

        Post savedPost = postRepository.save(post);

        log.info("Post with id: {} shares count updated for profile with id: {}", savedPost.getPostId(), profileId);

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
