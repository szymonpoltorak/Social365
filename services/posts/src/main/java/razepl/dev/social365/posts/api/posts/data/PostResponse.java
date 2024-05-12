package razepl.dev.social365.posts.api.posts.data;

import lombok.Builder;
import razepl.dev.social365.posts.clients.profile.data.Profile;

import java.time.LocalDateTime;

@Builder
public record PostResponse(String postId, boolean areNotificationTurnedOn, boolean isBookmarked,
                           LocalDateTime creationDateTime, String content, PostStatistics statistics,
                           boolean isPostLiked, Profile author) {
}
