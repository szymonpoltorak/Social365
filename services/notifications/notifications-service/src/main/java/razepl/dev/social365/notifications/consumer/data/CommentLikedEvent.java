package razepl.dev.social365.notifications.consumer.data;

public record CommentLikedEvent(String eventId, String timeStamp, String commentId, String targetProfileId,
                                String postId, int currentNumOfLikes, String likeAuthorFullName, String sourceProfileId) {
}
