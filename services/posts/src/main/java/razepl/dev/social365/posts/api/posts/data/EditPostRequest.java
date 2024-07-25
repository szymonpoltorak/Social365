package razepl.dev.social365.posts.api.posts.data;

import lombok.Builder;

@Builder
public record EditPostRequest(String authorId, String postId, String content, boolean hasAttachments) {
}
