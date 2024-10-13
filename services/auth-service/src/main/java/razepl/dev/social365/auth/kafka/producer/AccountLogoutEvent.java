package razepl.dev.social365.auth.kafka.producer;

import lombok.Builder;

@Builder
public record AccountLogoutEvent(String eventId, String timeStamp, String username) {
}
