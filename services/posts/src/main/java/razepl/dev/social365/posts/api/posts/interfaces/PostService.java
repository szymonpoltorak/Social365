package razepl.dev.social365.posts.api.posts.interfaces;

import razepl.dev.social365.posts.utils.pagination.data.PageInfo;
import razepl.dev.social365.posts.utils.pagination.interfaces.CassandraPage;

public interface PostService {

    int getUsersPostCount(String profileId);

    CassandraPage<PostData> getPostsOnPage(String profileId, PageInfo pageInfo);

    CassandraPage<PostData> getUsersPosts(String profileId, PageInfo pageInfo);

    PostData updateLikePostCount(String profileId, String postId);

    PostData updateNotificationStatus(String profileId, String postId);

    PostData updateBookmarkStatus(String profileId, String postId);

    PostData sharePost(String profileId, String postId, String content);

    PostData createPost(String profileId, String content, boolean hasAttachments);

    PostData editPost(String profileId, String postId, String content);

    PostData deletePost(String profileId, String postId);

}
