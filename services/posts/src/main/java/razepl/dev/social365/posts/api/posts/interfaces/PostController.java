package razepl.dev.social365.posts.api.posts.interfaces;

import razepl.dev.social365.posts.api.posts.data.PostResponse;
import razepl.dev.social365.posts.utils.pagination.data.PostsCassandraPage;

public interface PostController {

    int getUsersPostCount(String profileId);

    PostsCassandraPage<PostData> getPostsOnPage(String profileId, int pageNumber, int pageSize, String pagingState);

    PostData updateLikePostCount(String profileId, String postId);

    PostData updateNotificationStatus(String profileId, String postId);

    PostData updateBookmarkStatus(String profileId, String postId);

    PostData sharePost(String profileId, String postId, String content);

    PostData createPost(String profileId, String content, boolean hasAttachments);

    PostData editPost(String profileId, String postId, String content);

    PostResponse deletePost(String profileId, String postId);


}
