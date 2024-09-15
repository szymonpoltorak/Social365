package razepl.dev.social365.profile.producer.data;

import lombok.Builder;

@Builder
public record FriendshipEvent(String eventId, String timeStamp, String friendFullName,
                              String sourceProfileId, String targetProfileId) {
}
