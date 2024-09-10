package razepl.dev.social365.notifications.gateway.consumer;

import razepl.dev.social365.notifications.gateway.consumer.data.NotificationResponse;

@FunctionalInterface
public interface NotificationMapper {

    NotificationResponse mapNotification(String notification);

}
