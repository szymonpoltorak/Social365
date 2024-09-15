package razepl.dev.social365.notifications.gateway.consumer.data;

import lombok.Builder;

@Builder
public record NotificationResponse(String notificationId, String notificationText, String authorsProfileImageUrl,
                                   boolean isRead, String targetProfileId) {
}
