package razepl.dev.social365.posts.entities.comment.interfaces;

import java.time.LocalDateTime;

@FunctionalInterface
public interface CommentKeyData {

    LocalDateTime getCreationDateTime();

}
