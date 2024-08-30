package razepl.dev.social365.notifications.documents;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class PostLikedNotification extends Notification {

    private String postId;

    private int currentNumOfLikes;

    @Builder
    public PostLikedNotification(UUID notificationId, String eventId, String targetProfileId, String timestamp,
                                 String notificationText, String postId, int currentNumOfLikes) {
        super(notificationId, eventId, targetProfileId, timestamp, notificationText);

        this.postId = postId;
        this.currentNumOfLikes = currentNumOfLikes;
    }

}
