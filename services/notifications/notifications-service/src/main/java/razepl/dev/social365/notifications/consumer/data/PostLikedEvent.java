package razepl.dev.social365.notifications.consumer.data;

public record PostLikedEvent(String eventId, String timeStamp, String targetProfileId, String likeAuthorFullName,
                             String postId, int currentNumOfLikes, String sourceProfileId) {
}
