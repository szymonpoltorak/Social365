package razepl.dev.social365.images.api.data;

import lombok.Builder;

@Builder
public record PostImageResponse(long imageId, String username, String imagePath, String postId) {
}
