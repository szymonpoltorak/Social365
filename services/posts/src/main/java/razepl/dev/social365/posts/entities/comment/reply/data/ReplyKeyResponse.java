package razepl.dev.social365.posts.entities.comment.reply.data;

import lombok.Builder;
import razepl.dev.social365.posts.entities.comment.interfaces.CommentKeyData;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Builder
public record ReplyKeyResponse(String replyCommentId, String replyToCommentId, String creationDateTime) implements CommentKeyData {

    @Override
    public LocalDateTime getCreationDateTime() {
        return LocalDateTime.parse(creationDateTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

}
