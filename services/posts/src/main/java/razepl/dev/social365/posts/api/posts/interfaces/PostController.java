package razepl.dev.social365.posts.api.posts.interfaces;

import razepl.dev.social365.posts.api.posts.data.EditPostRequest;
import razepl.dev.social365.posts.utils.pagination.interfaces.CassandraPage;

public interface PostController {

    int getUsersPostCount(String profileId);

    CassandraPage<PostData> getPostsOnPage(String profileId, int pageNumber, int pageSize, String pagingState);

    CassandraPage<PostData> getUsersPosts(String profileId, int pageNumber, int pageSize, String pagingState);

    PostData updateLikePostCount(String profileId, String creationDateTime, String postId);

    PostData updateNotificationStatus(String profileId, String creationDateTime, String postId);

    PostData updateBookmarkStatus(String profileId, String creationDateTime, String postId);

    PostData sharePost(String profileId, String postId, String creationDateTime, String content);

    PostData createPost(String profileId, String content, boolean hasAttachments);

    PostData editPost(EditPostRequest editPostRequest);

    PostData deletePost(String profileId, String creationDateTime, String postId);

}
