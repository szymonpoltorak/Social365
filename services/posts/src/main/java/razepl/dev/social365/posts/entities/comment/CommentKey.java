package razepl.dev.social365.posts.entities.comment;

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
public class CommentKey {

    @PrimaryKeyColumn(
            value = "post_id",
            ordinal = 0,
            type = PrimaryKeyType.PARTITIONED
    )
    private UUID postId;

    @PrimaryKeyColumn(
            value = "comment_id",
            ordinal = 1,
            type = PrimaryKeyType.CLUSTERED,
            ordering = Ordering.ASCENDING
    )
    private UUID commentId;

    @PrimaryKeyColumn(
            value = "creation_date_time",
            ordinal = 2,
            type = PrimaryKeyType.CLUSTERED,
            ordering = Ordering.DESCENDING
    )
    private String creationDateTime;

}
