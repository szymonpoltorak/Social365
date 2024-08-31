package razepl.dev.social365.notifications.documents;

import razepl.dev.social365.notifications.api.notifications.data.NotificationResponse;

@FunctionalInterface
public interface NotificationsMapper {

    NotificationResponse mapNotificationToNotificationResponse(Notification notification);

}
