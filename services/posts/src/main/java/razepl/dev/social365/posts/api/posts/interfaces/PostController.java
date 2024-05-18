package razepl.dev.social365.posts.api.posts.interfaces;

import org.springframework.data.domain.Slice;
import razepl.dev.social365.posts.api.posts.data.PostResponse;

public interface PostController {

    int getUsersPostCount(String profileId);

    Slice<PostResponse> getPostsOnPage(String profileId, int pageSize, int pageNumber);

    PostResponse updateLikePostCount(String profileId, String postId);

    PostResponse updateNotificationStatus(String profileId, String postId);

    PostResponse updateBookmarkStatus(String profileId, String postId);

    PostResponse updateSharesCount(String profileId, String postId);

    PostResponse createPost(String profileId, String content, boolean hasAttachments);

    PostResponse editPost(String profileId, String postId, String content);

    PostResponse deletePost(String profileId, String postId);


}
