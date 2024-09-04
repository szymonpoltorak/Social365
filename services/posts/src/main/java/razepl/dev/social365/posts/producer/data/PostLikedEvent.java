package razepl.dev.social365.posts.producer.data;

import lombok.Builder;

@Builder
public record PostLikedEvent(String eventId, String timeStamp, String targetProfileId, String likeAuthorFullName,
                             String postId, int currentNumOfLikes, String sourceProfileId) {
}
