package razepl.dev.social365.posts.entities.comment.data;

import lombok.Builder;
import razepl.dev.social365.posts.entities.comment.interfaces.CommentKeyData;

@Builder
public record CommentKeyResponse(String postId, String commentId, String creationDateTime) implements CommentKeyData {
}
