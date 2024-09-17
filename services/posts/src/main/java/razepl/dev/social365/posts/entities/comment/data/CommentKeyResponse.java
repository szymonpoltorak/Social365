package razepl.dev.social365.posts.entities.comment.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import razepl.dev.social365.posts.api.constants.Params;
import razepl.dev.social365.posts.entities.comment.interfaces.CommentKeyData;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Builder
public record CommentKeyResponse(@JsonProperty(Params.POST_ID) String postId,
                                 @JsonProperty(Params.COMMENT_ID) String commentId,
                                 @JsonProperty(Params.CREATION_DATE_TIME) String creationDateTime) implements CommentKeyData {

    @Override
    @JsonIgnore
    public LocalDateTime getCreationDateTime() {
        return LocalDateTime.parse(creationDateTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

}
