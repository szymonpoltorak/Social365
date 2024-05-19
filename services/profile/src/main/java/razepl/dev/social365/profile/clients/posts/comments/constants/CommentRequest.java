package razepl.dev.social365.profile.clients.posts.comments.constants;

import lombok.Builder;

@Builder
public record CommentRequest(String objectId, String profileId, String content) {
}
