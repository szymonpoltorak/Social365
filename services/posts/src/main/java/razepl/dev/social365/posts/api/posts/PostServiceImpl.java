package razepl.dev.social365.posts.api.posts;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import razepl.dev.social365.posts.api.posts.data.PostResponse;
import razepl.dev.social365.posts.api.posts.interfaces.PostService;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    @Override
    public final Page<PostResponse> getPostsOnPage(String profileId, Pageable pageable) {
        return null;
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
