package razepl.dev.social365.posts.api.posts.data;

import lombok.Builder;
import razepl.dev.social365.posts.api.posts.interfaces.PostData;

@Builder
public record SharedPostResponse(PostData sharingPost, PostData sharedPost) implements PostData {
}
