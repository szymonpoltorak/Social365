package razepl.dev.social365.posts.entities.comment.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import razepl.dev.social365.posts.entities.comment.interfaces.CommentKeyData;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Builder
public record CommentKeyResponse(String postId, String commentId, String creationDateTime) implements CommentKeyData {

    @Override
    @JsonIgnore
    public LocalDateTime getCreationDateTime() {
        return LocalDateTime.parse(creationDateTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

}
