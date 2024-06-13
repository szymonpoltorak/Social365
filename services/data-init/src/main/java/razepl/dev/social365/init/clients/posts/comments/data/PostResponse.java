package razepl.dev.social365.init.clients.posts.comments.data;

import lombok.Builder;
import razepl.dev.social365.init.clients.posts.comments.PostData;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record PostResponse(String postId, boolean areNotificationTurnedOn, boolean isBookmarked,
                           LocalDateTime creationDateTime, String content, PostStatistics statistics,
                           boolean isPostLiked, ProfilePostResponse author, List<String> imageUrls) implements PostData {
}
