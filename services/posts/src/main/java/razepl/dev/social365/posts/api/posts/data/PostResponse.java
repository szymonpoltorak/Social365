package razepl.dev.social365.posts.api.posts.data;

import lombok.Builder;
import razepl.dev.social365.posts.api.posts.interfaces.PostData;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Builder
public record PostResponse(PostKeyResponse postKey, boolean areNotificationTurnedOn, boolean isBookmarked,
                           String content, PostStatistics statistics,
                           boolean isPostLiked, List<String> imageUrls) implements PostData {
    @Override
    public int compareTo(PostData postData) {
        return -getCreationDateTime().compareTo(postData.getCreationDateTime());
    }

    @Override
    public LocalDateTime getCreationDateTime() {
        return LocalDateTime.parse(postKey.creationDateTime(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

}
