package razepl.dev.social365.posts.entities.post.interfaces;

import razepl.dev.social365.posts.api.posts.interfaces.PostData;
import razepl.dev.social365.posts.entities.post.Post;

public interface PostMapper {

    PostData toPostResponse(Post post, String profileId);

    PostData toPostResponseNoImages(Post post, String profileId);

    PostData toPostData(Post post, String profileId);

    PostData toSharedPostResponse(Post sharingPost, String profileId);

    PostData toSharedPostResponse(Post sharingPost, Post sharedPost, String profileId);

}
