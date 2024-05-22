package razepl.dev.social365.init.clients.posts.comments.data;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record PostResponse(String postId, boolean areNotificationTurnedOn, boolean isBookmarked,
                           LocalDateTime creationDateTime, String content, PostStatistics statistics,
                           boolean isPostLiked, ProfilePostResponse author) {
}
