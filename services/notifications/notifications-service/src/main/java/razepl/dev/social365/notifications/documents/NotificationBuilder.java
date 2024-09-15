package razepl.dev.social365.notifications.documents;

import java.time.Instant;
import java.util.UUID;

public class NotificationBuilder {

    private UUID notificationId = null;
    private String eventId = null;
    private String targetProfileId = null;
    private Instant timestamp = null;
    private String notificationText = null;
    private String sourceProfileId = null;
    private boolean read = false;

    public final NotificationBuilder notificationId(UUID notificationId) {
        this.notificationId = notificationId;
        return this;
    }

    public final NotificationBuilder eventId(String eventId) {
        this.eventId = eventId;
        return this;
    }

    public final NotificationBuilder targetProfileId(String targetProfileId) {
        this.targetProfileId = targetProfileId;
        return this;
    }

    public final NotificationBuilder timestamp(Instant timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public final NotificationBuilder notificationText(String notificationText) {
        this.notificationText = notificationText;
        return this;
    }

    public final NotificationBuilder sourceProfileId(String sourceProfileId) {
        this.sourceProfileId = sourceProfileId;
        return this;
    }

    public final NotificationBuilder read(boolean read) {
        this.read = read;
        return this;
    }

    public final Notification build() {
        return new Notification(this.notificationId, this.eventId, this.targetProfileId, this.timestamp,
                this.notificationText, this.sourceProfileId, this.read);
    }

}
