package razepl.dev.social365.posts.entities.post;

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
@Table(value = "posts")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post {

    @PrimaryKey
    private UUID postId;

    private String authorId;

    private String content;

    private LocalDateTime creationDateTime;

    @CassandraType(type = CassandraType.Name.SET, typeArguments = CassandraType.Name.TEXT)
    private Set<String> userNotificationIds;

    @CassandraType(type = CassandraType.Name.SET, typeArguments = CassandraType.Name.TEXT)
    private Set<String> userLikedIds;

    @CassandraType(type = CassandraType.Name.SET, typeArguments = CassandraType.Name.TEXT)
    private Set<String> userSharedIds;

    @CassandraType(type = CassandraType.Name.SET, typeArguments = CassandraType.Name.TEXT)
    private Set<String> bookmarkedUserIds;

    @Version
    private long version;

    public final boolean areNotificationsTurnedOnBy(String profileId) {
        return userNotificationIds.contains(profileId);
    }

    public final boolean isBookmarkedBy(String profileId) {
        return bookmarkedUserIds.contains(profileId);
    }

    public final boolean isLikedBy(String profileId) {
        return userLikedIds.contains(profileId);
    }

    public final void sharePostByProfile(String profileId) {
        userSharedIds.add(profileId);
    }
}
