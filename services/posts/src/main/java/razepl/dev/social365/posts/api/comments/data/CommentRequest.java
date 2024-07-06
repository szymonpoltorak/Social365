package razepl.dev.social365.posts.api.comments.data;

import lombok.Builder;
import razepl.dev.social365.posts.entities.post.data.CommentKeyResponse;

@Builder
public record CommentRequest(CommentKeyResponse commentKey, String profileId, String content, boolean hasAttachment) {
}
