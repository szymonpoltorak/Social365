package razepl.dev.social365.posts.api.comments.data;

import lombok.Builder;
import razepl.dev.social365.posts.entities.comment.data.CommentKeyResponse;

@Builder
public record CommentDeleteRequest(CommentKeyResponse commentKey, String profileId) {
}
