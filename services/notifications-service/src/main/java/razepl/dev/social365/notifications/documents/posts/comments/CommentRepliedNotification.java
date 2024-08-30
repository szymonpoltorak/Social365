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
public class CommentRepliedNotification extends Notification {

    private String commentId;
    private int currentNumOfLikes;

    @Builder
    public CommentRepliedNotification(UUID notificationId, String eventId, String targetProfileId, String timestamp,
                                      String notificationText, int currentNumOfLikes, String commentId, String sourceProfileId) {
        super(notificationId, eventId, targetProfileId, timestamp, notificationText, sourceProfileId, false);

        this.currentNumOfLikes = currentNumOfLikes;
        this.commentId = commentId;
    }

}
