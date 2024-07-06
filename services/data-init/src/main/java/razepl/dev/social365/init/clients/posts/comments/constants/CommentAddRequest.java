package razepl.dev.social365.init.clients.posts.comments.constants;

import lombok.Builder;

@Builder
public record CommentAddRequest(String profileId, String content, boolean hasAttachment, String postId) {
}