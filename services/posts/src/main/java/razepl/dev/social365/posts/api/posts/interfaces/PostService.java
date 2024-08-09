package razepl.dev.social365.posts.api.posts.interfaces;

import razepl.dev.social365.posts.api.posts.data.EditPostRequest;
import razepl.dev.social365.posts.config.User;
import razepl.dev.social365.posts.utils.pagination.data.PageInfo;
import razepl.dev.social365.posts.utils.pagination.interfaces.CassandraPage;

public interface PostService {

    int getUsersPostCount(String profileId);

    CassandraPage<PostData> getPostsOnPage(String profileId, PageInfo pageInfo);

    CassandraPage<PostData> getUsersPosts(String profileId, PageInfo pageInfo);

    PostData updateLikePostCount(String profileId, String postId, String creationDateTime);

    PostData updateNotificationStatus(String profileId, String postId, String creationDateTime);

    PostData updateBookmarkStatus(String profileId, String postId, String creationDateTime);

    PostData sharePost(String profileId, String postId, String content, String creationDateTime);

    PostData createPost(String profileId, String content, boolean hasAttachments);

    PostData editPost(User user, EditPostRequest editPostRequest);

    PostData deletePost(String profileId, String postId, String creationDateTime);

}
