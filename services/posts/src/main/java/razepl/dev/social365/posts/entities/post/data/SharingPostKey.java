package razepl.dev.social365.posts.entities.post.data;

import java.util.UUID;

public record SharingPostKey(String creationDateTime, UUID postId) {

    public static SharingPostKey of(String creationDateTime, UUID postId) {
        return new SharingPostKey(creationDateTime, postId);
    }

}
