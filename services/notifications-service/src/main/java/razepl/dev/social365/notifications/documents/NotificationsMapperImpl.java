package razepl.dev.social365.notifications.documents;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import razepl.dev.social365.notifications.api.notifications.data.NotificationResponse;
import razepl.dev.social365.notifications.clients.images.ImageService;
import razepl.dev.social365.notifications.consumer.data.CommentLikedEvent;
import razepl.dev.social365.notifications.consumer.data.CommentRepliedEvent;
import razepl.dev.social365.notifications.consumer.data.PostCommentedEvent;
import razepl.dev.social365.notifications.consumer.data.PostLikedEvent;
import razepl.dev.social365.notifications.consumer.interfaces.KafkaMessageConverter;
import razepl.dev.social365.notifications.documents.posts.comments.CommentLikedNotification;
import razepl.dev.social365.notifications.documents.posts.comments.CommentRepliedNotification;
import razepl.dev.social365.notifications.documents.posts.comments.PostCommentedNotification;
import razepl.dev.social365.notifications.documents.posts.comments.PostLikedNotification;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class NotificationsMapperImpl implements NotificationsMapper {

    private final ImageService imageService;
    private final KafkaMessageConverter messageConverter;

    @Override
    public final NotificationResponse mapNotificationToNotificationResponse(Notification notification) {
        return NotificationResponse
                .builder()
                .notificationId(notification.getNotificationId().toString())
                .notificationText(notification.getNotificationText())
                .isRead(notification.isRead())
                .authorsProfileImageUrl(getUsersProfileImageUrl(notification.getSourceProfileId()))
                .build();
    }

    @Override
    public final PostLikedNotification mapPostLikedEventToPostLikedNotification(PostLikedEvent event) {
        String notificationsText = messageConverter.convert(event);

        return PostLikedNotification
                .builder()
                .notificationId(UUID.randomUUID())
                .eventId(event.eventId())
                .postId(event.postId())
                .currentNumOfLikes(event.currentNumOfLikes())
                .targetProfileId(event.targetProfileId())
                .timestamp(event.timeStamp())
                .sourceProfileId(event.sourceProfileId())
                .notificationText(notificationsText)
                .build();
    }

    @Override
    public final PostCommentedNotification mapPostCommentedEventToPostCommentedNotification(PostCommentedEvent event) {
        String notificationsText = messageConverter.convert(event);

        return PostCommentedNotification
                .builder()
                .notificationId(UUID.randomUUID())
                .eventId(event.eventId())
                .postId(event.postId())
                .targetProfileId(event.targetProfileId())
                .timestamp(event.timeStamp())
                .sourceProfileId(event.sourceProfileId())
                .notificationText(notificationsText)
                .build();
    }

    @Override
    public final CommentLikedNotification mapCommentLikedEventToCommentLikedNotification(CommentLikedEvent event) {
        String notificationsText = messageConverter.convert(event);

        return CommentLikedNotification
                .builder()
                .notificationId(UUID.randomUUID())
                .eventId(event.eventId())
                .commentId(event.commentId())
                .targetProfileId(event.targetProfileId())
                .timestamp(event.timeStamp())
                .sourceProfileId(event.sourceProfileId())
                .notificationText(notificationsText)
                .build();
    }

    @Override
    public final CommentRepliedNotification mapCommentRepliedEventToCommentRepliedNotification(CommentRepliedEvent event) {
        String notificationsText = messageConverter.convert(event);

        return CommentRepliedNotification
                .builder()
                .notificationId(UUID.randomUUID())
                .eventId(event.eventId())
                .commentId(event.commentId())
                .targetProfileId(event.targetProfileId())
                .timestamp(event.timeStamp())
                .sourceProfileId(event.sourceProfileId())
                .notificationText(notificationsText)
                .build();
    }

    private String getUsersProfileImageUrl(String sourceProfileId) {
        return imageService.getProfileImageByProfileId(sourceProfileId).imagePath();
    }

}
