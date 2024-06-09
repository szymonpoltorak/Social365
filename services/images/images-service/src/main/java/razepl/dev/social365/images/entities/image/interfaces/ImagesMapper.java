package razepl.dev.social365.images.entities.image.interfaces;

import razepl.dev.social365.images.api.data.CommentImageResponse;
import razepl.dev.social365.images.api.data.ImageResponse;
import razepl.dev.social365.images.api.data.PostImageResponse;
import razepl.dev.social365.images.entities.image.Image;
import razepl.dev.social365.images.entities.image.comment.CommentImage;
import razepl.dev.social365.images.entities.image.post.PostImage;


public interface ImagesMapper {

    ImageResponse toImageResponse(Image image);

    PostImageResponse toPostImageResponse(PostImage postImage);

    CommentImageResponse toCommentResponse(CommentImage savedImage);
}
