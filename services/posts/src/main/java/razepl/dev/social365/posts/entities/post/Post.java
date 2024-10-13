package razepl.dev.social365.posts.entities.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Version;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;
import razepl.dev.social365.posts.entities.post.data.SharingPostKey;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Table(value = "posts")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post {

    @PrimaryKey
    private PostKey key;

    private String content;

    @Column(value = "has_attachments")
    private boolean hasAttachments;

    @Column(value = "original_post_id")
    private UUID originalPostId;

    @Column(value = "original_post_author_id")
    private String originalPostAuthorId;

    @Column(value = "user_notification_ids")
    private Set<String> userNotificationIds;

    @Column(value = "user_liked_ids")
    private Set<String> userLikedIds;

    @Column(value = "user_shared_ids")
    private Set<String> userSharedIds;

    @Version
    private long version;

    public final UUID getPostId() {
        return key.getPostId();
    }

    public final SharingPostKey getSharingPostKey() {
        return SharingPostKey.of(originalPostAuthorId, originalPostId);
    }

    public final boolean isAuthorId(String profileId) {
        return key.getAuthorId().equals(profileId);
    }

    public final String getAuthorId() {
        return key.getAuthorId();
    }

    public final String getCreationDateTime() {
        return key.getCreationDateTime();
    }

    public final boolean isSharedPost() {
        return originalPostId != null && originalPostAuthorId != null;
    }

    public final void addUserLikedId(String profileId) {
        if (userLikedIds == null) {
            userLikedIds = new HashSet<>();
        }
        userLikedIds.add(profileId);
    }

    public final void addUserNotificationId(String profileId) {
        if (userNotificationIds == null) {
            userNotificationIds = new HashSet<>();
        }
        userNotificationIds.add(profileId);
    }

    public final void sharePostByProfile(String profileId) {
        if (userSharedIds == null) {
            userSharedIds = new HashSet<>();
        }
        userSharedIds.add(profileId);
    }

    public final int getLikesCount() {
        if (userLikedIds == null) {
            return 0;
        }
        return userLikedIds.size();
    }

    public final int getSharesCount() {
        if (userSharedIds == null) {
            return 0;
        }
        return userSharedIds.size();
    }

    public final boolean areNotificationsTurnedOnBy(String profileId) {
        return userNotificationIds != null && userNotificationIds.contains(profileId);
    }

    public final boolean isLikedBy(String profileId) {
        return userLikedIds != null && userLikedIds.contains(profileId);
    }

}
