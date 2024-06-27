package razepl.dev.social365.posts.entities.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Version;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

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

    public final UUID getPostId() {
        return key.getPostId();
    }

    public final String getAuthorId() {
        return key.getAuthorId();
    }

    public final String getCreationDateTime() {
        return key.getCreationDateTime();
    }

    public final boolean isSharedPost() {
        return originalPostId != null;
    }

    public final void addUserLikedId(String profileId) {
        if (userLikedIds == null) {
            userLikedIds = Set.of(profileId);
        } else {
            userLikedIds.add(profileId);
        }
    }

    public final void addUserSharedId(String profileId) {
        if (userSharedIds == null) {
            userSharedIds = Set.of(profileId);
        } else {
            userSharedIds.add(profileId);
        }
    }

    public final void addUserNotificationId(String profileId) {
        if (userNotificationIds == null) {
            userNotificationIds = Set.of(profileId);
        } else {
            userNotificationIds.add(profileId);
        }
    }

    public final void addBookmarkedUserId(String profileId) {
        if (bookmarkedUserIds == null) {
            bookmarkedUserIds = Set.of(profileId);
        } else {
            bookmarkedUserIds.add(profileId);
        }
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

    public final boolean isBookmarkedBy(String profileId) {
        return bookmarkedUserIds != null && bookmarkedUserIds.contains(profileId);
    }

    public final boolean isLikedBy(String profileId) {
        return userLikedIds != null && userLikedIds.contains(profileId);
    }

    public final void sharePostByProfile(String profileId) {
        userSharedIds.add(profileId);
    }
}
