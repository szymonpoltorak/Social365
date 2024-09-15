package razepl.dev.social365.profile.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import razepl.dev.social365.profile.config.kafka.KafkaConfigNames;
import razepl.dev.social365.profile.nodes.profile.Profile;
import razepl.dev.social365.profile.producer.data.FriendshipEvent;
import razepl.dev.social365.profile.producer.data.FriendshipRejectedEvent;

import java.time.Instant;
import java.util.UUID;

@Slf4j
@Service
public class KafkaProducerImpl implements KafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper jsonMapper;
    private final String friendshipAcceptedTopic;
    private final String friendshipRejectedTopic;
    private final String friendshipFollowedTopic;
    private final String friendshipRequestedTopic;

    public KafkaProducerImpl(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper jsonMapper,
                             @Value(KafkaConfigNames.FRIENDSHIP_ACCEPTED_TOPIC) String friendshipAcceptedTopic,
                             @Value(KafkaConfigNames.FRIENDSHIP_REJECTED_TOPIC) String friendshipRejectedTopic,
                             @Value(KafkaConfigNames.FRIENDSHIP_FOLLOWED_TOPIC) String friendshipFollowedTopic,
                             @Value(KafkaConfigNames.FRIENDSHIP_REQUESTED_TOPIC) String friendshipRequestedTopic) {
        this.kafkaTemplate = kafkaTemplate;
        this.jsonMapper = jsonMapper;
        this.friendshipAcceptedTopic = friendshipAcceptedTopic;
        this.friendshipRejectedTopic = friendshipRejectedTopic;
        this.friendshipFollowedTopic = friendshipFollowedTopic;
        this.friendshipRequestedTopic = friendshipRequestedTopic;
    }

    @Override
    public final void sendFriendshipAcceptedEvent(Profile targetProfile, Profile sourceProfile) {
        log.info("Sending friendship accepted event for target profile: {} and source profile: {}", targetProfile, sourceProfile);

        FriendshipEvent event = toFriendshipEvent(targetProfile, sourceProfile);

        log.info("Sending friendship accepted event: {}", event);

        sendMessage(friendshipAcceptedTopic, event);
    }

    @Override
    public final void sendFriendshipRequestedEvent(Profile targetProfile, Profile sourceProfile) {
        log.info("Sending friendship requested event for target profile: {} and source profile: {}", targetProfile, sourceProfile);

        FriendshipEvent event = toFriendshipEvent(targetProfile, sourceProfile);

        log.info("Sending friendship requested event: {}", event);

        sendMessage(friendshipRequestedTopic, event);
    }

    @Override
    public final void sendFriendshipFollowedEvent(Profile targetProfile, Profile sourceProfile) {
        log.info("Sending friendship followed event for target profile: {} and source profile: {}", targetProfile, sourceProfile);

        FriendshipEvent event = toFriendshipEvent(targetProfile, sourceProfile);

        log.info("Sending friendship followed event: {}", event);

        sendMessage(friendshipFollowedTopic, event);
    }

    @Override
    public final void sendFriendshipRejectedEvent(Profile targetProfile, Profile sourceProfile) {
        log.info("Sending friendship rejected event for target profile: {} and source profile: {}", targetProfile, sourceProfile);

        FriendshipRejectedEvent event = FriendshipRejectedEvent
                .builder()
                .eventId(UUID.randomUUID().toString())
                .timeStamp(Instant.now().toString())
                .sourceProfileId(sourceProfile.getProfileId())
                .targetProfileId(targetProfile.getProfileId())
                .build();

        log.info("Sending friendship rejected event: {}", event);

        sendMessage(friendshipRejectedTopic, event);
    }

    private FriendshipEvent toFriendshipEvent(Profile targetProfile, Profile sourceProfile) {
        return FriendshipEvent
                .builder()
                .eventId(UUID.randomUUID().toString())
                .timeStamp(Instant.now().toString())
                .friendFullName(sourceProfile.getFullName())
                .sourceProfileId(sourceProfile.getProfileId())
                .targetProfileId(targetProfile.getProfileId())
                .build();
    }

    private void sendMessage(String topic, Object event) {
        try {
            String eventJson = jsonMapper.writeValueAsString(event);

            kafkaTemplate.send(topic, eventJson);

        } catch (JsonProcessingException exception) {
            log.error("Failed to convert event to JSON: {}", event, exception);
        }
    }

}
