package razepl.dev.social365.notifications.documents;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.UUID;

@Data
@Document
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    @Id
    private UUID notificationId;

    private String eventId;

    private String targetProfileId;

    private Instant timestamp;

    private String notificationText;

    private String sourceProfileId;

    private boolean read;

    public static NotificationBuilder notificationBuilder() {
        return new NotificationBuilder();
    }

}
