package razepl.dev.social365.posts.api.posts;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import razepl.dev.social365.posts.api.posts.data.PostResponse;
import razepl.dev.social365.posts.api.posts.interfaces.PostService;
import razepl.dev.social365.posts.clients.profile.ProfileService;
import razepl.dev.social365.posts.entities.post.Post;
import razepl.dev.social365.posts.entities.post.interfaces.PostMapper;
import razepl.dev.social365.posts.entities.post.interfaces.PostRepository;
import razepl.dev.social365.posts.exceptions.PostDoesNotExistException;
import razepl.dev.social365.posts.exceptions.UserIsNotAuthorException;

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

    @Override
    public final Page<PostResponse> getPostsOnPage(String profileId, Pageable pageable) {

        log.info("Getting posts for profileId: {}, with pageable : {}", profileId, pageable);

        List<String> friendsIds = profileService.getFriendsIds(profileId);

        log.info("Found {} friends for profile with id: {}", friendsIds.size(), profileId);

        Page<Post> posts = postRepository.findAllByFollowedUserIds(friendsIds, pageable);

        log.info("Found {} posts for profile with id: {}", posts.getTotalElements(), profileId);

        return posts.map(post -> postMapper.toPostResponse(post, profileId));
    }

    //TODO: Implement handling images
    @Override
    public final PostResponse createPost(String profileId, String content) {
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
    public final PostResponse editPost(String profileId, String postId, String content) {
        log.info("Editing post with id: {} for profileId: {}, with content: {}", postId, profileId, content);

        Post post = postRepository.findById(UUID.fromString(postId))
                .orElseThrow(() -> new PostDoesNotExistException(postId));

        log.info("Found post with id: {}", post);

        if (!post.getAuthorId().equals(profileId)) {
            throw new UserIsNotAuthorException(profileId);
        }
        post.setContent(content);

        Post savedPost = postRepository.save(post);

        log.info("Post with id: {} edited for profile with id: {}", savedPost.getPostId(), profileId);

        return postMapper.toPostResponse(savedPost, profileId);
    }

    @Override
    public final PostResponse updateLikePostCount(String profileId, String postId) {
        return null;
    }

    @Override
    public final PostResponse updateNotificationStatus(String profileId, String postId) {
        return null;
    }

    @Override
    public final PostResponse updateBookmarkStatus(String profileId, String postId) {
        return null;
    }

    @Override
    public final PostResponse updateSharesCount(String profileId, String postId) {
        return null;
    }

    @Override
    public final PostResponse deletePost(String profileId, String postId) {
        return null;
    }

}
