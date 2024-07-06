package razepl.dev.social365.posts.entities.post.data;

import lombok.Builder;

@Builder
public record CommentKeyResponse(String postId, String commentId, String creationDateTime) {
}
