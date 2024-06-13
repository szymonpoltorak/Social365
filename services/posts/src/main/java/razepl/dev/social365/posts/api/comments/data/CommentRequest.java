package razepl.dev.social365.posts.api.comments.data;

import lombok.Builder;

@Builder
public record CommentRequest(String objectId, String profileId, String content, String replyToCommentId, boolean hasAttachment) {
}
