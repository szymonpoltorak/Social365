package razepl.dev.social365.posts.entities.post.interfaces;

import razepl.dev.social365.posts.api.posts.interfaces.PostData;
import razepl.dev.social365.posts.entities.post.Post;

@FunctionalInterface
public interface PostMapper {

    PostData toPostResponse(Post post, String profileId);

}
