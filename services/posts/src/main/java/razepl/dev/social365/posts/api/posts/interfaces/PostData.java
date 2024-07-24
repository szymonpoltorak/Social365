package razepl.dev.social365.posts.api.posts.interfaces;

import java.time.LocalDateTime;

public interface PostData extends Comparable<PostData> {

    LocalDateTime getCreationDateTime();

}
