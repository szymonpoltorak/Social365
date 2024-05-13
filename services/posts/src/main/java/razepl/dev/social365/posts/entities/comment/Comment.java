package razepl.dev.social365.posts.entities.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Version;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@Table(value = "comments")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    @PrimaryKey
    private UUID commentId;

    @PrimaryKey
    private UUID postId;

    private String authorId;

    private String content;

    private LocalDateTime creationDateTime;

    @CassandraType(type = CassandraType.Name.SET, typeArguments = CassandraType.Name.TEXT)
    private Set<String> userLikedIds;

    @Version
    private long version;

    public final boolean isLikedBy(String profileId) {
        return userLikedIds.contains(profileId);
    }

    public final int getLikesCount() {
        return userLikedIds.size();
    }
}
