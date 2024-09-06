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
public class CommentLikedNotification extends Notification {

    private String replyCommentId;
    private String commentId;

    @Builder
    public CommentLikedNotification(UUID notificationId, String eventId, String targetProfileId, String timestamp,
                                    String notificationText, String replyCommentId, String commentId, String sourceProfileId) {
        super(notificationId, eventId, targetProfileId, timestamp, notificationText, sourceProfileId, false);

        this.replyCommentId = replyCommentId;
        this.commentId = commentId;
    }

}
