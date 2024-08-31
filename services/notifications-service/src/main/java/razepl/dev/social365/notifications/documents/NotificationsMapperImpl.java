package razepl.dev.social365.notifications.documents;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import razepl.dev.social365.notifications.api.notifications.data.NotificationResponse;
import razepl.dev.social365.notifications.clients.images.ImageService;

@Component
@RequiredArgsConstructor
public class NotificationsMapperImpl implements NotificationsMapper {

    private final ImageService imageService;

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

    private String getUsersProfileImageUrl(String sourceProfileId) {
        return imageService.getProfileImageByProfileId(sourceProfileId).imagePath();
    }

}
