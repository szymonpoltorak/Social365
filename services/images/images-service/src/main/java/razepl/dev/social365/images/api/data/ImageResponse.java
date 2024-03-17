package razepl.dev.social365.images.api.data;

import lombok.Builder;

@Builder
public record ImageResponse(long imageId, String username, String imagePath) {
}
