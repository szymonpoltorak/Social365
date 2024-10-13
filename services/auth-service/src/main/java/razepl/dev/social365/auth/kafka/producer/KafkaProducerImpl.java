package razepl.dev.social365.auth.kafka.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import razepl.dev.social365.auth.config.kafka.KafkaConfigNames;
import razepl.dev.social365.auth.entities.user.User;

import java.time.Instant;
import java.util.UUID;

@Slf4j
@Service
public class KafkaProducerImpl implements KafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final String accountCreatedTopic;
    private final String accountLogoutTopic;
    private final ObjectMapper objectMapper;

    public KafkaProducerImpl(KafkaTemplate<String, String> kafkaTemplate,
                             ObjectMapper objectMapper,
                             @Value(KafkaConfigNames.ACCOUNT_LOGOUT_TOPIC) String accountLogoutTopic,
                             @Value(KafkaConfigNames.ACCOUNT_CREATED_TOPIC) String accountCreatedTopic) {
        this.kafkaTemplate = kafkaTemplate;
        this.accountCreatedTopic = accountCreatedTopic;
        this.accountLogoutTopic = accountLogoutTopic;
        this.objectMapper = objectMapper;
    }

    @Override
    public final void sendAccountCreatedEvent(String username) {
        log.info("Sending account created event for user: {}", username);

        AccountCreatedEvent event = AccountCreatedEvent
                .builder()
                .eventId(UUID.randomUUID().toString())
                .username(username)
                .timeStamp(Instant.now().toString())
                .build();

        log.info("Sending account created event: {}", event);

        try {
            kafkaTemplate.send(accountCreatedTopic, objectMapper.writeValueAsString(event));
        } catch (JsonProcessingException e) {
            log.error("Error while sending account created event: {}", e.getMessage());
        }
    }

    @Override
    public final void sendAccountLogoutEvent(User user) {
        log.info("Sending account logout event for user: {}", user);

        AccountLogoutEvent event = AccountLogoutEvent
                .builder()
                .eventId(UUID.randomUUID().toString())
                .username(user.getUsername())
                .timeStamp(Instant.now().toString())
                .build();

        log.info("Sending account logout event: {}", event);

        try {
            kafkaTemplate.send(accountLogoutTopic, objectMapper.writeValueAsString(event));
        } catch (JsonProcessingException e) {
            log.error("Error while sending account logout event: {}", e.getMessage());
        }
    }

}
