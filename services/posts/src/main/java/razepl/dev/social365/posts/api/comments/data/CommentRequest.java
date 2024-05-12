package razepl.dev.social365.posts.api.comments.data;

import lombok.Builder;

@Builder
public record CommentRequest(String postId, String profileId, String content) {
}
