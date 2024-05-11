package razepl.dev.social365.posts.api.posts.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import razepl.dev.social365.posts.api.posts.data.PostResponse;

public interface PostService {

    Page<PostResponse> getPostsOnPage(String profileId, Pageable pageable);

    PostResponse updateLikePostCount(String profileId, String postId);

    PostResponse updateNotificationStatus(String profileId, String postId);

    PostResponse updateBookmarkStatus(String profileId, String postId);

    PostResponse updateSharesCount(String profileId, String postId);

    PostResponse createPost(String profileId, String content);

    PostResponse editPost(String profileId, String postId, String content);

    PostResponse deletePost(String profileId, String postId);

}
