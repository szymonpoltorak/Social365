package razepl.dev.social365.posts.api.comments.interfaces;

import razepl.dev.social365.posts.api.comments.data.CommentAddRequest;
import razepl.dev.social365.posts.api.comments.data.CommentDeleteRequest;
import razepl.dev.social365.posts.api.comments.data.CommentEditRequest;
import razepl.dev.social365.posts.api.comments.data.CommentResponse;
import razepl.dev.social365.posts.api.comments.data.LikeCommentRequest;
import razepl.dev.social365.posts.utils.pagination.interfaces.CassandraPage;

public interface CommentController {

    CassandraPage<CommentResponse> getCommentsForPost(String postId, String profileId, int pageSize, String pagingState);

    CommentResponse addCommentToPost(CommentAddRequest commentRequest);

    CommentResponse editComment(CommentEditRequest commentEditRequest);

    CommentResponse deleteComment(CommentDeleteRequest commentRequest);

    CommentResponse updateLikeCommentCount(LikeCommentRequest likeCommentRequest);

}
