package razepl.dev.social365.notifications.clients.images.data;

import lombok.Builder;

@Builder
public record ImageResponse(long imageId, String username, String imagePath) {
}
