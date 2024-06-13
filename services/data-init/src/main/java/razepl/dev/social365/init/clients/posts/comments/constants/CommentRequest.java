package razepl.dev.social365.init.clients.posts.comments.constants;

import lombok.Builder;

@Builder
public record CommentRequest(String objectId, String profileId, String content, String replyToCommentId, boolean hasAttachment) {
}