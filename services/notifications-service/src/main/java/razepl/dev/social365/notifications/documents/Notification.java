package razepl.dev.social365.notifications.documents;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Getter
@Setter
@Builder
@Document
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    @Id
    private UUID notificationId;

    private String eventId;

    private String targetProfileId;

    private String timestamp;

    private String notificationText;

    private String sourceProfileId;

    private boolean read;

}
