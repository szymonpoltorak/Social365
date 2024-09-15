package razepl.dev.social365.notifications.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import razepl.dev.social365.notifications.api.notifications.data.NotificationResponse;
import razepl.dev.social365.notifications.config.kafka.KafkaConfigNames;
import razepl.dev.social365.notifications.config.rabbitmq.RabbitMQSettings;
import razepl.dev.social365.notifications.consumer.data.CommentLikedEvent;
import razepl.dev.social365.notifications.consumer.data.CommentRepliedEvent;
import razepl.dev.social365.notifications.consumer.data.FriendshipEvent;
import razepl.dev.social365.notifications.consumer.data.FriendshipRejectedEvent;
import razepl.dev.social365.notifications.consumer.data.PostCommentedEvent;
import razepl.dev.social365.notifications.consumer.data.PostLikedEvent;
import razepl.dev.social365.notifications.consumer.interfaces.KafkaMessageConverter;
import razepl.dev.social365.notifications.documents.Notification;
import razepl.dev.social365.notifications.documents.NotificationRepository;
import razepl.dev.social365.notifications.documents.NotificationsMapper;
import razepl.dev.social365.notifications.documents.posts.comments.CommentLikedNotification;
import razepl.dev.social365.notifications.documents.posts.comments.CommentRepliedNotification;
import razepl.dev.social365.notifications.documents.posts.comments.PostCommentedNotification;
import razepl.dev.social365.notifications.documents.posts.comments.PostLikedNotification;

import java.time.Instant;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationsConsumer {

    private final KafkaMessageConverter messageConverter;
    private final NotificationRepository notificationRepository;
    private final RabbitTemplate rabbitTemplate;
    private final NotificationsMapper notificationsMapper;
    private final ObjectMapper jsonMapper;
    private final Jwt kafkaJwt;

    @KafkaListener(
            topics = KafkaConfigNames.FRIENDSHIP_REQUESTED_TOPIC,
            groupId = KafkaConfigNames.NOTIFICATIONS_GROUP_ID,
            properties = KafkaConfigNames.FRIENDSHIP_EVENT_TYPE_PROPERTY
    )
    public final void consumeFriendshipRequested(FriendshipEvent event) {
        log.info("Consumed friendship.requested event: {}", event);

        sendNotificationToRabbitMQ(event, FriendshipTopic.REQUESTED);
    }

    @KafkaListener(
            topics = KafkaConfigNames.FRIENDSHIP_ACCEPTED_TOPIC,
            groupId = KafkaConfigNames.NOTIFICATIONS_GROUP_ID,
            properties = KafkaConfigNames.FRIENDSHIP_EVENT_TYPE_PROPERTY
    )
    public final void consumeFriendshipAccepted(FriendshipEvent event) {
        log.info("Consumed friendship.accepted event: {}", event);

        sendNotificationToRabbitMQ(event, FriendshipTopic.ACCEPTED);
    }

    @KafkaListener(
            topics = KafkaConfigNames.FRIENDSHIP_FOLLOWED_TOPIC,
            groupId = KafkaConfigNames.NOTIFICATIONS_GROUP_ID,
            properties = KafkaConfigNames.FRIENDSHIP_EVENT_TYPE_PROPERTY
    )
    public final void consumeFriendshipFollowed(FriendshipEvent event) {
        log.info("Consumed friendship.followed event: {}", event);

        sendNotificationToRabbitMQ(event, FriendshipTopic.FOLLOWED);
    }

    @KafkaListener(
            topics = KafkaConfigNames.FRIENDSHIP_REJECTED_TOPIC,
            groupId = KafkaConfigNames.NOTIFICATIONS_GROUP_ID,
            properties = KafkaConfigNames.FRIENDSHIP_REJECTED_EVENT_TYPE_PROPERTY
    )
    public final void consumeFriendshipRejected(FriendshipRejectedEvent event) {
        log.info("Consumed friendship.rejected event: {}", event);

        notificationRepository.deleteBySourceProfileIdAndTargetProfileId(event.sourceProfileId(), event.targetProfileId());
    }

    @KafkaListener(
            topics = KafkaConfigNames.POST_LIKED_TOPIC,
            groupId = KafkaConfigNames.NOTIFICATIONS_GROUP_ID,
            properties = KafkaConfigNames.POST_LIKED_EVENT_TYPE_PROPERTY
    )
    public final void consumePostLiked(PostLikedEvent event) {
        log.info("Consumed post.liked event: {}", event);

        PostLikedNotification notification = notificationsMapper.mapPostLikedEventToPostLikedNotification(event);

        log.info("Notification from post liked event: {}", notification);

        sendNotification(notification);
    }

    @KafkaListener(
            topics = KafkaConfigNames.POST_COMMENTED_TOPIC,
            groupId = KafkaConfigNames.NOTIFICATIONS_GROUP_ID,
            properties = KafkaConfigNames.POST_COMMENTED_EVENT_TYPE_PROPERTY
    )
    public final void consumePostCommented(PostCommentedEvent event) {
        log.info("Consumed post.commented event: {}", event);

        PostCommentedNotification notification = notificationsMapper.mapPostCommentedEventToPostCommentedNotification(event);

        log.info("Notification from post commented event: {}", notification);

        sendNotification(notification);
    }

    @KafkaListener(
            topics = KafkaConfigNames.COMMENT_LIKED_TOPIC,
            groupId = KafkaConfigNames.NOTIFICATIONS_GROUP_ID,
            properties = KafkaConfigNames.COMMENT_LIKED_EVENT_TYPE_PROPERTY
    )
    public final void consumeCommentLiked(CommentLikedEvent event) {
        log.info("Consumed comment.liked event: {}", event);

        CommentLikedNotification notification = notificationsMapper.mapCommentLikedEventToCommentLikedNotification(event);

        log.info("Notification from comment liked event: {}", notification);

        sendNotification(notification);
    }

    @KafkaListener(
            topics = KafkaConfigNames.COMMENT_REPLIED_TOPIC,
            groupId = KafkaConfigNames.NOTIFICATIONS_GROUP_ID,
            properties = KafkaConfigNames.COMMENT_REPLIED_EVENT_TYPE_PROPERTY
    )
    public final void consumeCommentReplied(CommentRepliedEvent event) {
        log.info("Consumed comment.replied event: {}", event);

        CommentRepliedNotification notification = notificationsMapper.mapCommentRepliedEventToCommentRepliedNotification(event);

        log.info("Notification from comment replied event: {}", notification);

        sendNotification(notification);
    }

    private void sendNotificationToRabbitMQ(FriendshipEvent event, FriendshipTopic topic) {
        String notificationsText = messageConverter.convert(event, topic);

        Notification notification = Notification
                .notificationBuilder()
                .notificationId(UUID.randomUUID())
                .eventId(event.eventId())
                .read(false)
                .timestamp(Instant.parse(event.timeStamp()))
                .sourceProfileId(event.sourceProfileId())
                .targetProfileId(event.targetProfileId())
                .notificationText(notificationsText)
                .build();

        log.info("Notification from friendship event: {}, topic : {}", notification, topic.name());

        sendNotification(notification);
    }

    private void sendNotification(Notification notification) {
        notificationRepository.save(notification);

        SecurityContextHolder.getContext().setAuthentication(new JwtAuthenticationToken(kafkaJwt));

        NotificationResponse notificationResponse = notificationsMapper.mapNotificationToNotificationResponse(notification);

        log.info("Sending notification to RabbitMQ: {}", notificationResponse);

        try {
            rabbitTemplate.convertAndSend(
                    RabbitMQSettings.NOTIFICATIONS_EXCHANGE_NAME,
                    RabbitMQSettings.NOTIFICATIONS_ROUTING_KEY,
                    jsonMapper.writeValueAsString(notificationResponse)
            );
        } catch (JsonProcessingException exception) {
            log.error("Error while writing notification object to json : {}", exception.getMessage());
        }
    }

}
