package razepl.dev.social365.posts.entities.comment.reply.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import razepl.dev.social365.posts.api.constants.Params;
import razepl.dev.social365.posts.entities.comment.interfaces.CommentKeyData;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Builder
public record ReplyKeyResponse(@JsonProperty(Params.REPLY_COMMENT_ID) String replyCommentId,
                               @JsonProperty(Params.REPLY_TO_COMMENT_ID) String replyToCommentId,
                               @JsonProperty(Params.CREATION_DATE_TIME) String creationDateTime) implements CommentKeyData {

    @Override
    @JsonIgnore
    public LocalDateTime getCreationDateTime() {
        return LocalDateTime.parse(creationDateTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

}
