package razepl.dev.social365.posts.api.comments.interfaces;

import razepl.dev.social365.posts.api.comments.data.CommentAddRequest;
import razepl.dev.social365.posts.api.comments.data.CommentEditRequest;
import razepl.dev.social365.posts.api.comments.data.CommentResponse;
import razepl.dev.social365.posts.config.User;
import razepl.dev.social365.posts.entities.comment.data.CommentKeyResponse;
import razepl.dev.social365.posts.utils.pagination.interfaces.CassandraPage;

public interface CommentController {

    CassandraPage<CommentResponse> getCommentsForPost(String postId, User user, int pageSize, String pagingState);

    CommentResponse addCommentToPost(User user, CommentAddRequest commentRequest);

    CommentResponse editComment(User user, CommentEditRequest commentEditRequest);

    CommentResponse deleteComment(User user, CommentKeyResponse commentKey);

    CommentResponse updateLikeCommentCount(User user, CommentKeyResponse commentKey);

}
