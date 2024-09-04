package razepl.dev.social365.posts.producer.data;

import lombok.Builder;

@Builder
public record CommentRepliedEvent(String eventId, String timeStamp, String sourceProfileId, String targetProfileId,
                                  String commentId, String replyCommentId, String replyAuthorFullName) {
}
