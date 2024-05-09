package razepl.dev.social365.posts.entities.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Version;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@Table
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    @PrimaryKey
    private UUID commentId;

    private UUID postId;

    private String authorId;

    private String content;

    private LocalDateTime creationDateTime;

    private Set<String> userLikedIds;

    @Version
    private long version;

}
