package razepl.dev.social365.notifications.documents.profiles;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import razepl.dev.social365.notifications.documents.Notification;

import java.util.UUID;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class FriendshipNotification extends Notification {

    private String sourceProfileId;

    @Builder
    public FriendshipNotification(UUID notificationId, String eventId, String targetProfileId, String timestamp,
                                  String notificationText, String sourceProfileId) {
        super(notificationId, eventId, targetProfileId, timestamp, notificationText);

        this.sourceProfileId = sourceProfileId;
    }

}
