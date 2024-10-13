package razepl.dev.social365.profile.kafka.consumer;

import lombok.Builder;

@Builder
public record AccountLogoutEvent(String eventId, String timeStamp, String username) {
}
