package razepl.dev.social365.profile.clients.posts.comments.constants;

import lombok.Builder;
import razepl.dev.social365.profile.api.profile.data.ProfilePostResponse;

import java.time.LocalDateTime;

@Builder
public record CommentResponse(String commentId, int commentLikesCount, String content, ProfilePostResponse author,
                              LocalDateTime creationDateTime, boolean isLiked) {
}
