package razepl.dev.social365.posts.entities.post.interfaces;

import razepl.dev.social365.posts.api.posts.data.PostResponse;
import razepl.dev.social365.posts.entities.post.Post;

@FunctionalInterface
public interface PostMapper {

    PostResponse toPostResponse(Post post, String profileId);

}
