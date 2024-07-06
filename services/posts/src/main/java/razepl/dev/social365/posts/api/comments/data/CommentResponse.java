package razepl.dev.social365.posts.api.comments.data;

import lombok.Builder;
import razepl.dev.social365.posts.clients.profile.data.Profile;
import razepl.dev.social365.posts.entities.post.data.CommentKeyResponse;

@Builder
public record CommentResponse(CommentKeyResponse commentKey, int commentLikesCount, String content, Profile author,
                              boolean isLiked, String imageUrl) {
}
