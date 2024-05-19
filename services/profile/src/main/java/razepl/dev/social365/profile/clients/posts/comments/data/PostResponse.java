package razepl.dev.social365.profile.clients.posts.comments.data;

import lombok.Builder;
import razepl.dev.social365.profile.api.profile.data.ProfilePostResponse;

import java.time.LocalDateTime;

@Builder
public record PostResponse(String postId, boolean areNotificationTurnedOn, boolean isBookmarked,
                           LocalDateTime creationDateTime, String content, PostStatistics statistics,
                           boolean isPostLiked, ProfilePostResponse author) {
}
