package razepl.dev.social365.posts.api.comments.replies.data;

import lombok.Builder;

@Builder
public record ReplyAddRequest(String content, boolean hasAttachment, String commentId) {
}
