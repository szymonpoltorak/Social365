package razepl.dev.social365.notifications.api.notifications.interfaces;

import org.springframework.data.domain.Pageable;
import razepl.dev.social365.notifications.api.notifications.data.NotificationResponse;
import razepl.dev.social365.notifications.config.auth.User;
import razepl.dev.social365.notifications.utils.pagination.SocialPage;

@FunctionalInterface
public interface NotificationsService {

    SocialPage<NotificationResponse> getNotificationsForUser(User user, Pageable pageable);

}
