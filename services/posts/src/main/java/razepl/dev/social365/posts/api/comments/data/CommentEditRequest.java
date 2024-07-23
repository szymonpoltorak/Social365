package razepl.dev.social365.posts.api.comments.data;

import lombok.Builder;
import razepl.dev.social365.posts.entities.comment.data.CommentKeyResponse;

@Builder
public record CommentEditRequest(CommentKeyResponse commentKey, String profileId, String content, boolean hasAttachment) {
}
