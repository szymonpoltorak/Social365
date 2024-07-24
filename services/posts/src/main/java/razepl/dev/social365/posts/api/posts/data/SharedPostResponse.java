package razepl.dev.social365.posts.api.posts.data;

import lombok.Builder;
import razepl.dev.social365.posts.api.posts.interfaces.PostData;

import java.time.LocalDateTime;

@Builder
public record SharedPostResponse(PostData sharingPost, PostData sharedPost) implements PostData {

    @Override
    public int compareTo(PostData postData) {
        return sharingPost.compareTo(postData);
    }

    @Override
    public LocalDateTime getCreationDateTime() {
        return sharingPost.getCreationDateTime();
    }

}
