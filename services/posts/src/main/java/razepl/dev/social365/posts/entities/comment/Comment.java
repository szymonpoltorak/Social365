package razepl.dev.social365.posts.entities.comment;

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
@Table(value = "comments")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    @PrimaryKey
    private CommentKey key;

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
        return key.getCommentId();
    }

    public final UUID getPostId() {
        return key.getPostId();
    }

    public final boolean isLikedBy(String profileId) {
        return userLikedIds != null && userLikedIds.contains(profileId);
    }

    public final int getLikesCount() {
        return userLikedIds != null ? userLikedIds.size() : 0;
    }
}
