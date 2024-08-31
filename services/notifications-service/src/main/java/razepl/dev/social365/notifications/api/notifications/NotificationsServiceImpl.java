package razepl.dev.social365.notifications.api.notifications;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import razepl.dev.social365.notifications.api.notifications.data.NotificationResponse;
import razepl.dev.social365.notifications.api.notifications.interfaces.NotificationsService;
import razepl.dev.social365.notifications.config.auth.User;
import razepl.dev.social365.notifications.documents.Notification;
import razepl.dev.social365.notifications.documents.NotificationRepository;
import razepl.dev.social365.notifications.documents.NotificationsMapper;
import razepl.dev.social365.notifications.utils.pagination.SocialPage;
import razepl.dev.social365.notifications.utils.pagination.SocialPageImpl;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationsServiceImpl implements NotificationsService {

    private final NotificationRepository notificationsRepository;
    private final NotificationsMapper notificationsMapper;

    @Override
    public final SocialPage<NotificationResponse> getNotificationsForUser(User user, Pageable pageable) {
        log.info("Getting notifications for user: {}, with pageable : {}", user, pageable);

        Slice<Notification> notifications = notificationsRepository.findAllByTargetProfileId(user.profileId(), pageable);

        log.info("Found {} notifications for user: {}", notifications.getNumberOfElements(), user);

        return SocialPageImpl.of(notifications.map(notificationsMapper::mapNotificationToNotificationResponse));
    }

}
