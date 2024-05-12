package razepl.dev.social365.posts.api.comments.data;

import lombok.Builder;
import razepl.dev.social365.posts.clients.profile.data.Profile;

import java.time.LocalDateTime;

@Builder
public record CommentResponse(String commentId, int commentLikesCount, String content, Profile author,
                              LocalDateTime creationDateTime, boolean isLiked) {
}
