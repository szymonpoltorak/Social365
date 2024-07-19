package razepl.dev.social365.posts.api.comments.replies.interfaces;

import razepl.dev.social365.posts.api.comments.data.CommentResponse;
import razepl.dev.social365.posts.api.comments.replies.data.LikeReplyRequest;
import razepl.dev.social365.posts.api.comments.replies.data.ReplyAddRequest;
import razepl.dev.social365.posts.api.comments.replies.data.ReplyDeleteRequest;
import razepl.dev.social365.posts.api.comments.replies.data.ReplyEditRequest;
import razepl.dev.social365.posts.utils.pagination.data.PageInfo;
import razepl.dev.social365.posts.utils.pagination.interfaces.CassandraPage;

public interface RepliesService {

    CassandraPage<CommentResponse> getRepliesForComment(String commentId, String profileId, PageInfo pageInfo);

    CommentResponse addReplyToComment(ReplyAddRequest commentRequest);

    CommentResponse editReplyComment(ReplyEditRequest commentRequest);

    CommentResponse deleteReplyComment(ReplyDeleteRequest commentRequest);

    CommentResponse updateLikeCommentCount(LikeReplyRequest likeCommentRequest);

}
