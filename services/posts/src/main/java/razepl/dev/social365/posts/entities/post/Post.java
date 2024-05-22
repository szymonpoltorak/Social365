package razepl.dev.social365.posts.entities.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Version;
import org.springframework.data.cassandra.core.mapping.Column;
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
    @Column(value = "post_id")
    private UUID postId;

    @Column(value = "author_id")
    private String authorId;

    private String content;

    @Column(value = "creation_date_time")
    private LocalDateTime creationDateTime;

    @Column(value = "has_attachments")
    private boolean hasAttachments;

    @Column(value = "user_notification_ids")
    private Set<String> userNotificationIds;

    @Column(value = "user_liked_ids")
    private Set<String> userLikedIds;

    @Column(value = "user_shared_ids")
    private Set<String> userSharedIds;

    @Column(value = "bookmarked_user_ids")
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
