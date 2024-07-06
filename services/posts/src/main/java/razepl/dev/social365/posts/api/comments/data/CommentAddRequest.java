package razepl.dev.social365.posts.api.comments.data;

import lombok.Builder;

@Builder
public record CommentAddRequest(String profileId, String content, boolean hasAttachment, String postId) {
}
