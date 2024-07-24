package razepl.dev.social365.posts.api.posts.data;

import lombok.Builder;
import razepl.dev.social365.posts.api.posts.interfaces.PostData;
import razepl.dev.social365.posts.clients.profile.data.Profile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Builder
public record PostResponse(String postId, boolean areNotificationTurnedOn, boolean isBookmarked,
                           String creationDateTime, String content, PostStatistics statistics,
                           boolean isPostLiked, Profile author, List<String> imageUrls) implements PostData {
    @Override
    public int compareTo(PostData postData) {
        return -getCreationDateTime().compareTo(postData.getCreationDateTime());
    }

    @Override
    public LocalDateTime getCreationDateTime() {
        return LocalDateTime.parse(creationDateTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

}
