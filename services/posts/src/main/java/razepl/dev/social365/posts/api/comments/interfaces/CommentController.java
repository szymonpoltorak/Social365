package razepl.dev.social365.posts.api.comments.interfaces;

import razepl.dev.social365.posts.api.comments.data.CommentRequest;
import razepl.dev.social365.posts.api.comments.data.CommentResponse;
import razepl.dev.social365.posts.utils.pagination.data.PageInfo;
import razepl.dev.social365.posts.utils.pagination.interfaces.CassandraPage;

public interface CommentController {

    CassandraPage<CommentResponse> getRepliesForComment(String commentId, String profileId, int pageSize, String pagingState);

    CassandraPage<CommentResponse> getCommentsForPost(String postId, String profileId, int pageSize, String pagingState);

    CommentResponse addCommentToPost(CommentRequest commentRequest);

    CommentResponse editComment(CommentRequest commentRequest);

    CommentResponse deleteComment(String commentId, String profileId);

}
