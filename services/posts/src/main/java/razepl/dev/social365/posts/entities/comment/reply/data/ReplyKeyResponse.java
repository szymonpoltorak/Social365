package razepl.dev.social365.posts.entities.comment.reply.data;

import lombok.Builder;
import razepl.dev.social365.posts.entities.comment.interfaces.CommentKeyData;

@Builder
public record ReplyKeyResponse(String replyCommentId, String replyToCommentId, String creationDateTime) implements CommentKeyData {
}
