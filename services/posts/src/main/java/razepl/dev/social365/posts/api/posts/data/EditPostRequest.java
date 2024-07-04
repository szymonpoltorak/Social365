package razepl.dev.social365.posts.api.posts.data;

import lombok.Builder;

@Builder
public record EditPostRequest(String profileId, String postId, String creationDateTime,
                              String content, boolean hasAttachments) {
}
