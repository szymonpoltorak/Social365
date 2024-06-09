package razepl.dev.social365.posts.api.posts.interfaces;

import razepl.dev.social365.posts.api.posts.data.DataPage;
import razepl.dev.social365.posts.api.posts.data.PostResponse;

public interface PostService {

    int getUsersPostCount(String profileId);

    DataPage<PostData> getPostsOnPage(String profileId, int pageNumber, int pageSize);

    PostData updateLikePostCount(String profileId, String postId);

    PostData updateNotificationStatus(String profileId, String postId);

    PostData updateBookmarkStatus(String profileId, String postId);

    PostData sharePost(String profileId, String postId, String content);

    PostData createPost(String profileId, String content, boolean hasAttachments);

    PostData editPost(String profileId, String postId, String content);

    PostResponse deletePost(String profileId, String postId);

}
