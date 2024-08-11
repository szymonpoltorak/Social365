package razepl.dev.social365.init.clients.api.constants;

import lombok.Builder;
import razepl.dev.social365.init.clients.api.data.ProfilePostResponse;

import java.time.LocalDateTime;

@Builder
public record CommentResponse(String commentId, int commentLikesCount, String content, ProfilePostResponse author,
                              LocalDateTime creationDateTime, boolean isLiked) {
}
