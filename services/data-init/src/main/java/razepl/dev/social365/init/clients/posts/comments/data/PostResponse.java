package razepl.dev.social365.init.clients.posts.comments.data;

import lombok.Builder;
import razepl.dev.social365.init.clients.posts.comments.PostData;

import java.util.List;

@Builder
public record PostResponse(PostKeyResponse postKey, boolean areNotificationTurnedOn, boolean isBookmarked,
                           String content, PostStatistics statistics,
                           boolean isPostLiked, List<String> imageUrls) implements PostData {

}
