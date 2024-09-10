package razepl.dev.social365.notifications.gateway.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import razepl.dev.social365.notifications.gateway.config.rabbitmq.RabbitMQSettings;
import razepl.dev.social365.notifications.gateway.consumer.data.NotificationResponse;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationsConsumer {

    private static final String WEBSOCKET_NOTIFICATIONS_DESTINATION = "/queue/notifications";

    private final NotificationMapper notificationMapper;
    private final SimpMessagingTemplate websocketTemplate;

    @RabbitListener(queues = RabbitMQSettings.NOTIFICATIONS_QUEUE_NAME)
    public final void consumeNotification(String notificationMessage) {
        log.info("Received notification: {}", notificationMessage);

        NotificationResponse notification = notificationMapper.mapNotification(notificationMessage);

        log.info("Sending notification: {}", notification);

        websocketTemplate.convertAndSendToUser(notification.targetProfileId(), WEBSOCKET_NOTIFICATIONS_DESTINATION, notification);
    }

}
