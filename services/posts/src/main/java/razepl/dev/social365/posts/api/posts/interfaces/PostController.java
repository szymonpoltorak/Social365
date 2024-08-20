package razepl.dev.social365.posts.api.posts.interfaces;

import razepl.dev.social365.posts.api.posts.data.EditPostRequest;
import razepl.dev.social365.posts.config.User;
import razepl.dev.social365.posts.utils.pagination.interfaces.SocialPage;

public interface PostController {

    int getUsersPostCount(String profileId);

    SocialPage<PostData> getPostsOnPage(User user, int pageNumber, int pageSize, String pagingState);

    SocialPage<PostData> getUsersPosts(String profileId, int pageNumber, int pageSize, String pagingState);

    PostData updateLikePostCount(User user, String creationDateTime, String postId);

    PostData updateNotificationStatus(User user, String creationDateTime, String postId);

    PostData updateBookmarkStatus(User user, String creationDateTime, String postId);

    PostData sharePost(User user, String postId, String creationDateTime, String content);

    PostData createPost(User user, String content, boolean hasAttachments);

    PostData editPost(User user, EditPostRequest editPostRequest);

    PostData deletePost(User user, String authorId, String postId);

}
