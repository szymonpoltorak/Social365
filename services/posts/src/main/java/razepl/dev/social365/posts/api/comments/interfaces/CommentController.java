package razepl.dev.social365.posts.api.comments.interfaces;

import razepl.dev.social365.posts.api.comments.data.CommentRequest;
import razepl.dev.social365.posts.api.comments.data.CommentResponse;
import razepl.dev.social365.posts.api.comments.data.PageInfo;
import razepl.dev.social365.posts.api.posts.data.DataPage;

public interface CommentController {

    DataPage<CommentResponse> getRepliesForComment(String commentId, String profileId, PageInfo pageInfo);

    DataPage<CommentResponse> getCommentsForPost(String postId, String profileId, PageInfo pageInfo);

    CommentResponse addCommentToPost(CommentRequest commentRequest);

    CommentResponse editComment(CommentRequest commentRequest);

    CommentResponse deleteComment(String commentId, String profileId);

}
