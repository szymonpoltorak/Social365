package razepl.dev.social365.profile.kafka.producer.data;

import lombok.Builder;

@Builder
public record FriendshipRejectedEvent(String eventId, String timeStamp, String sourceProfileId, String targetProfileId) {
}
