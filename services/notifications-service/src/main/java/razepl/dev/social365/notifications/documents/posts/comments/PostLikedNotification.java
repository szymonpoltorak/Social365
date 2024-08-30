package razepl.dev.social365.notifications.documents.posts.comments;

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
public class PostLikedNotification extends Notification {

    private String postId;
    private int currentNumOfLikes;

    @Builder
    public PostLikedNotification(UUID notificationId, String eventId, String targetProfileId, String timestamp,
                                 String notificationText, String postId, int currentNumOfLikes, String sourceProfileId) {
        super(notificationId, eventId, targetProfileId, timestamp, notificationText, sourceProfileId, false);

        this.postId = postId;
        this.currentNumOfLikes = currentNumOfLikes;
    }

}
