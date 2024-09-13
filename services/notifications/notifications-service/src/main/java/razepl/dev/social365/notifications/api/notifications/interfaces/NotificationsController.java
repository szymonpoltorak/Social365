package razepl.dev.social365.notifications.api.notifications.interfaces;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import razepl.dev.social365.notifications.api.notifications.data.NotificationResponse;
import razepl.dev.social365.notifications.config.auth.User;
import razepl.dev.social365.notifications.utils.pagination.SocialPage;
import razepl.dev.social365.notifications.utils.pagination.SocialPageImpl;

@Tag(name = "NotificationsController", description = "Operations pertaining to notifications in Social365")
public interface NotificationsController {

    @Operation(summary = "Get notifications for user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved notifications", content = @Content(schema = @Schema(implementation = SocialPageImpl.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    SocialPage<NotificationResponse> getNotificationsForUser(User user, int page, int size);

    @Operation(summary = "Read notifications by user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully read notifications"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    NotificationResponse readNotifications(User user);

}
