package razepl.dev.social365.notifications.api.notifications;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import razepl.dev.social365.notifications.api.notifications.constants.NotificationsMappings;
import razepl.dev.social365.notifications.api.notifications.constants.Params;
import razepl.dev.social365.notifications.api.notifications.data.NotificationResponse;
import razepl.dev.social365.notifications.api.notifications.interfaces.NotificationsController;
import razepl.dev.social365.notifications.api.notifications.interfaces.NotificationsService;
import razepl.dev.social365.notifications.config.auth.AuthUser;
import razepl.dev.social365.notifications.config.auth.User;
import razepl.dev.social365.notifications.utils.pagination.SocialPage;

@RestController
@RequiredArgsConstructor
@RequestMapping(NotificationsMappings.NOTIFICATIONS_MAPPING)
public class NotificationsControllerImpl implements NotificationsController {

    private final NotificationsService notificationsService;

    @Override
    @GetMapping
    public final SocialPage<NotificationResponse> getNotificationsForUser(@AuthUser User user,
                                                                          @RequestParam(Params.PAGE_NUMBER) int pageNumber,
                                                                          @RequestParam(Params.PAGE_SIZE) int pageSize) {
        return notificationsService.getNotificationsForUser(user, PageRequest.of(pageNumber, pageSize));
    }

}
