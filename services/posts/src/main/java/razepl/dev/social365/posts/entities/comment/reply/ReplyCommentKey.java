package razepl.dev.social365.posts.entities.comment.reply;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

import java.util.UUID;

@Data
@Builder
@PrimaryKeyClass
@AllArgsConstructor
public class ReplyCommentKey {

    @PrimaryKeyColumn(value = "reply_comment_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private UUID replyCommentId;

    @PrimaryKeyColumn(value = "reply_to_comment_id", ordinal = 1, type = PrimaryKeyType.PARTITIONED)
    private UUID replyToCommentId;

    @PrimaryKeyColumn(value = "creation_date_time", ordinal = 2, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
    private String creationDateTime;

}
