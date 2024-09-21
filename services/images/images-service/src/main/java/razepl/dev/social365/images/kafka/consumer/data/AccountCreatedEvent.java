package razepl.dev.social365.images.kafka.consumer.data;

import lombok.Builder;

@Builder
public record AccountCreatedEvent(String eventId, String username, String timeStamp) {
}
