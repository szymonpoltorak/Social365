package razepl.dev.social365.notifications.api.notifications.data;

import lombok.Builder;

@Builder
public record NotificationResponse(String notificationId, String notificationText, String authorsProfileImageUrl,
                                   boolean isRead) {
}
