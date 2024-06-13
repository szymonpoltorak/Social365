package razepl.dev.social365.images.api.data;

import lombok.Builder;

@Builder
public record CommentImageResponse(long imageId, String username, String imagePath, String commentId) {
}
