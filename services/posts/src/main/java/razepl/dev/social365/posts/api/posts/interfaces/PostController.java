package razepl.dev.social365.posts.api.posts.interfaces;

import razepl.dev.social365.posts.api.posts.data.DataPage;
import razepl.dev.social365.posts.api.posts.data.PostResponse;

public interface PostController {

    int getUsersPostCount(String profileId);

    DataPage<PostData>  getPostsOnPage(String profileId, int pageSize, int pageNumber);

    PostData updateLikePostCount(String profileId, String postId);

    PostData updateNotificationStatus(String profileId, String postId);

    PostData updateBookmarkStatus(String profileId, String postId);

    PostData updateSharesCount(String profileId, String postId);

    PostData createPost(String profileId, String content, boolean hasAttachments);

    PostData editPost(String profileId, String postId, String content);

    PostResponse deletePost(String profileId, String postId);


}
