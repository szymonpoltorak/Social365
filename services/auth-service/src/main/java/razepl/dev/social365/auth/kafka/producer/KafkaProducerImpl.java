package razepl.dev.social365.auth.kafka.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import razepl.dev.social365.auth.config.kafka.KafkaConfigNames;

@Slf4j
@Service
public class KafkaProducerImpl implements KafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final String accountCreatedTopic;

    public KafkaProducerImpl(KafkaTemplate<String, String> kafkaTemplate,
                             @Value(KafkaConfigNames.ACCOUNT_CREATED_TOPIC) String accountCreatedTopic) {
        this.kafkaTemplate = kafkaTemplate;
        this.accountCreatedTopic = accountCreatedTopic;
    }

    @Override
    public final void sendAccountCreatedEvent(String username) {
        log.info("Sending account created event for user: {}", username);

        kafkaTemplate.send(accountCreatedTopic, username);
    }

}
