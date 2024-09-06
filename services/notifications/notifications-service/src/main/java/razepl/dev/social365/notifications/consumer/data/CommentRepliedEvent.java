package razepl.dev.social365.notifications.consumer.data;

public record CommentRepliedEvent(String eventId, String timeStamp, String sourceProfileId, String targetProfileId,
                                  String commentId, String replyCommentId, String replyAuthorFullName) {
}
