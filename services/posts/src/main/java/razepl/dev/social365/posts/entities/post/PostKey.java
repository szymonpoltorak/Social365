package razepl.dev.social365.posts.entities.post;

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
@AllArgsConstructor(staticName = "of")
@PrimaryKeyClass
public final class PostKey {

    @PrimaryKeyColumn(
            name = "author_id",
            ordinal = 0,
            type = PrimaryKeyType.PARTITIONED
    )
    private String authorId;

    @PrimaryKeyColumn(
            name = "post_id",
            ordinal = 1,
            type = PrimaryKeyType.CLUSTERED,
            ordering = Ordering.ASCENDING
    )
    private UUID postId;

    @PrimaryKeyColumn(
            name = "creation_date_time",
            ordinal = 2,
            type = PrimaryKeyType.CLUSTERED,
            ordering = Ordering.DESCENDING
    )
    private String creationDateTime;

}
