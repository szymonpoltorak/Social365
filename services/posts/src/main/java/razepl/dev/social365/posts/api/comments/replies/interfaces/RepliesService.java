package razepl.dev.social365.posts.api.comments.replies.interfaces;

import razepl.dev.social365.posts.api.comments.data.CommentResponse;
import razepl.dev.social365.posts.api.comments.replies.data.ReplyAddRequest;
import razepl.dev.social365.posts.api.comments.replies.data.ReplyEditRequest;
import razepl.dev.social365.posts.config.User;
import razepl.dev.social365.posts.entities.comment.reply.data.ReplyKeyResponse;
import razepl.dev.social365.posts.utils.pagination.data.PageInfo;
import razepl.dev.social365.posts.utils.pagination.interfaces.SocialPage;

public interface RepliesService {

    SocialPage<CommentResponse> getRepliesForComment(String commentId, String profileId, PageInfo pageInfo);

    CommentResponse addReplyToComment(User user, ReplyAddRequest commentRequest);

    CommentResponse editReplyComment(User user, ReplyEditRequest commentRequest);

    CommentResponse deleteReplyComment(User user, ReplyKeyResponse replyKey);

    CommentResponse updateLikeCommentCount(User user,ReplyKeyResponse replyKey);

}
