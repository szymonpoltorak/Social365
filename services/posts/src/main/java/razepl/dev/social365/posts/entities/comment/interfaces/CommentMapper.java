package razepl.dev.social365.posts.entities.comment.interfaces;

import razepl.dev.social365.posts.api.comments.data.CommentResponse;
import razepl.dev.social365.posts.entities.comment.Comment;
import razepl.dev.social365.posts.entities.comment.CommentKey;
import razepl.dev.social365.posts.entities.comment.data.CommentKeyResponse;
import razepl.dev.social365.posts.entities.comment.reply.ReplyComment;
import razepl.dev.social365.posts.entities.comment.reply.ReplyCommentKey;
import razepl.dev.social365.posts.entities.comment.reply.data.ReplyKeyResponse;

public interface CommentMapper {

    CommentResponse toCommentResponse(Comment comment, String profileId);

    CommentResponse toCommentResponse(ReplyComment comment, String profileId);

    CommentKey toCommentKey(CommentKeyResponse commentKeyResponse);

    ReplyCommentKey toCommenyReplyKey(ReplyKeyResponse replyKeyResponse);

}
