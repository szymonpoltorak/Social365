package razepl.dev.social365.posts.clients.images.data;

import lombok.Builder;

@Builder
public record CommentImage(long imageId, String username, String imagePath, String commentId) {
}
