package razepl.dev.social365.posts.api.comments.replies.interfaces;

import razepl.dev.social365.posts.api.comments.data.CommentResponse;
import razepl.dev.social365.posts.api.comments.replies.data.ReplyAddRequest;
import razepl.dev.social365.posts.api.comments.replies.data.ReplyDeleteRequest;
import razepl.dev.social365.posts.api.comments.replies.data.ReplyEditRequest;
import razepl.dev.social365.posts.utils.pagination.interfaces.CassandraPage;

public interface RepliesController {

    CassandraPage<CommentResponse> getRepliesForComment(String commentId, String profileId, int pageSize, String pagingState);

    CommentResponse addReplyToComment(ReplyAddRequest commentRequest);

    CommentResponse editReplyComment(ReplyEditRequest commentRequest);

    CommentResponse deleteReplyComment(ReplyDeleteRequest commentRequest);

}
