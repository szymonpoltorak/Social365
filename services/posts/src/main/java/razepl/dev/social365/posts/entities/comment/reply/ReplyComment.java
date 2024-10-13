package razepl.dev.social365.posts.entities.comment.reply;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Version;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Table(value = "reply_comments")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReplyComment {

    @PrimaryKey
    private ReplyCommentKey key;

    @Column(value = "author_id")
    private String authorId;

    private String content;

    @Column(value = "has_attachments")
    private boolean hasAttachments;

    @Column(value = "user_liked_ids")
    private Set<String> userLikedIds;

    @Column(value = "has_replies")
    private boolean hasReplies;

    @Version
    private long version;

    public final UUID getCommentId() {
        return key.getReplyToCommentId();
    }

    public final UUID getReplyCommentId() {
        return key.getReplyCommentId();
    }

    public final boolean isLikedBy(String profileId) {
        return userLikedIds != null && userLikedIds.contains(profileId);
    }

    public final int getLikesCount() {
        return userLikedIds != null ? userLikedIds.size() : 0;
    }

    public final void addUserLikedId(String profileId) {
        if (userLikedIds == null) {
            userLikedIds = new HashSet<>();
        }
        userLikedIds.add(profileId);
    }

    public final boolean isAuthor(String profileId) {
        return profileId.equals(authorId);
    }

}
