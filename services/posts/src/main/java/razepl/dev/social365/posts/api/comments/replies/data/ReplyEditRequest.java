package razepl.dev.social365.posts.api.comments.replies.data;

import lombok.Builder;
import razepl.dev.social365.posts.entities.comment.reply.data.ReplyKeyResponse;

@Builder
public record ReplyEditRequest(ReplyKeyResponse replyKey, String profileId, String content, boolean hasAttachment) {
}
