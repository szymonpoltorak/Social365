package razepl.dev.social365.posts.api.posts;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
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
import razepl.dev.social365.posts.utils.validators.interfaces.PostValidator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
    public Slice<PostResponse> getPostsOnPage(String profileId, int pageSize, int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        int currentPageNumber = pageable.getPageNumber();
        int currentPageSize = pageable.getPageSize();
        boolean hasNext;
        Collection<Post> result = new ArrayList<>(pageable.getPageSize() + 1);

        log.info("Getting posts for profileId: {}, with pageable : {}", profileId, pageable);

        while (true) {
            Page<String> friendsIds = profileService.getFriendsIds(profileId, pageNumber);

            log.info("Found {} friends for profile with id: {}", friendsIds.getTotalElements(), profileId);

            Slice<Post> posts = postRepository.findAllByFollowedUserIds(friendsIds.toList(), pageable);

            log.info("Found {} posts for profile with id: {}", posts.getNumberOfElements(), profileId);

            result.addAll(posts.getContent());

            if (result.size() >= pageable.getPageSize() || !posts.hasNext()) {
                hasNext = posts.hasNext();

                break;
            }
            pageable = PageRequest.of(++currentPageNumber, currentPageSize - result.size());
        }
        log.info("Found posts returning result...");

        List<PostResponse> slice = result
                .parallelStream()
                .map(post -> postMapper.toPostResponse(post, profileId))
                .toList();

        return new SliceImpl<>(slice, pageable, hasNext);
    }

    @Override
    public PostResponse createPost(String profileId, String content, boolean hasAttachments) {
        log.info("Creating post for profileId: {}, with content: {}", profileId, content);

        postValidator.validatePostContent(content);

        Post post = Post
                .builder()
                .authorId(profileId)
                .content(content)
                .creationDateTime(LocalDateTime.now())
                .hasAttachments(hasAttachments)
                .build();

        Post savedPost = postRepository.save(post);

        log.info("Post with id: {} created for profile with id: {}", savedPost.getPostId(), profileId);

        return postMapper.toPostResponse(savedPost, profileId);
    }

    @Override
    public PostResponse editPost(String profileId, String postId, String content) {
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
    public PostResponse updateLikePostCount(String profileId, String postId) {
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
