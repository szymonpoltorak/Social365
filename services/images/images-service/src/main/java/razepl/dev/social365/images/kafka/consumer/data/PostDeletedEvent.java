package razepl.dev.social365.images.kafka.consumer.data;

import lombok.Builder;

@Builder
public record PostDeletedEvent(String eventId, String postId, String timeStamp) {
}
