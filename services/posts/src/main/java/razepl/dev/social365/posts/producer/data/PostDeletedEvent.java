package razepl.dev.social365.posts.producer.data;

import lombok.Builder;

@Builder
public record PostDeletedEvent(String eventId, String postId, String timeStamp) {
}
