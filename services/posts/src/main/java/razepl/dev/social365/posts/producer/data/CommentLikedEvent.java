package razepl.dev.social365.posts.producer.data;

import lombok.Builder;

@Builder
public record CommentLikedEvent(String eventId, String timeStamp, String commentId, String targetProfileId, String username,
                                String postId, int currentNumOfLikes, String likeAuthorFullName, String sourceProfileId) {
}
