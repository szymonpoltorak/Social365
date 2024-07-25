package razepl.dev.social365.posts.entities.post.data;

import java.util.UUID;

public record SharingPostKey(String authorId, UUID postId) {

    public static SharingPostKey of(String authorId, UUID postId) {
        return new SharingPostKey(authorId, postId);
    }

}
