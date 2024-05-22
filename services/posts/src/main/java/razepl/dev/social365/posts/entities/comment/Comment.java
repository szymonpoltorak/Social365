package razepl.dev.social365.posts.entities.comment;

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
@Table(value = "comments")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    @PrimaryKey
    @Column(value = "comment_id")
    private UUID commentId;

    @Column(value = "post_id")
    private UUID postId;

    @Column(value = "author_id")
    private String authorId;

    private String content;

    @Column(value = "creation_date_time")
    private LocalDateTime creationDateTime;

    @Column(value = "user_liked_ids")
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
