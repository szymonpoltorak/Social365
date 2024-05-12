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

import java.util.List;

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
    public final PostResponse createPost(String profileId, String content) {
        return null;
    }

    @Override
    public final PostResponse editPost(String profileId, String postId, String content) {
        return null;
    }

    @Override
    public final PostResponse deletePost(String profileId, String postId) {
        return null;
    }

}
