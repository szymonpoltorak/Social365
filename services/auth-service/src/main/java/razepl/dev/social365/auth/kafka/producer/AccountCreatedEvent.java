package razepl.dev.social365.auth.kafka.producer;

import lombok.Builder;

@Builder
public record AccountCreatedEvent(String eventId, String username, String timeStamp) {
}
