package razepl.dev.social365.posts.api.posts.interfaces;

import razepl.dev.social365.posts.api.posts.data.EditPostRequest;
import razepl.dev.social365.posts.config.auth.User;
import razepl.dev.social365.posts.utils.pagination.data.PageInfo;
import razepl.dev.social365.posts.utils.pagination.interfaces.SocialPage;

public interface PostService {

    int getUsersPostCount(String profileId);

    SocialPage<PostData> getPostsOnPage(String profileId, PageInfo pageInfo);

    SocialPage<PostData> getUsersPosts(String profileId, PageInfo pageInfo);

    PostData updateLikePostCount(User user, String postId, String creationDateTime);

    PostData updateNotificationStatus(String profileId, String postId, String creationDateTime);

    PostData updateBookmarkStatus(String profileId, String postId, String creationDateTime);

    PostData sharePost(String profileId, String postId, String content, String creationDateTime);

    PostData createPost(String profileId, String content, boolean hasAttachments);

    PostData editPost(User user, EditPostRequest editPostRequest);

    PostData deletePost(String profileId, String postId, String creationDateTime);

}
