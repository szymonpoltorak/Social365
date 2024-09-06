package razepl.dev.social365.notifications.consumer.data;

public record PostCommentedEvent(String eventId, String timeStamp, String targetProfileId, String commentAuthorFullName,
                                  String postId, String commentId, String sourceProfileId) {
}
