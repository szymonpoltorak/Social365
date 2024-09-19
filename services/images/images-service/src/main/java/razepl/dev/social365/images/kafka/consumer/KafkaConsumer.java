package razepl.dev.social365.images.kafka.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import razepl.dev.social365.images.api.interfaces.FileManagementService;
import razepl.dev.social365.images.config.kafka.KafkaConfigNames;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaConsumer {

    private final FileManagementService fileManagementService;

    @KafkaListener(
            topics = KafkaConfigNames.ACCOUNT_CREATED_TOPIC,
            groupId = KafkaConfigNames.NOTIFICATIONS_GROUP_ID
    )
    public final void consumeAccountCreatedEvent(String username) {
        log.info("Consumed account.created event: {}", username);

        fileManagementService.createProfileFolder(username);
    }

}
