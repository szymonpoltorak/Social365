package razepl.dev.social365.init.clients.api.constants;

import lombok.Builder;

@Builder
public record CommentAddRequest(String content, boolean hasAttachment, String postId) {
}