package razepl.dev.social365.posts.producer.data;

import lombok.Builder;

@Builder
public record PostCommentedEvent(String eventId, String timeStamp, String targetProfileId, String commentAuthorFullName,
                                  String postId, String commentId, String sourceProfileId, String username) {
}
