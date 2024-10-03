package razepl.dev.social365.images.entities.image;

import org.springframework.stereotype.Component;
import razepl.dev.social365.images.api.data.CommentImageResponse;
import razepl.dev.social365.images.api.data.ImageResponse;
import razepl.dev.social365.images.api.data.PostImageResponse;
import razepl.dev.social365.images.entities.image.comment.CommentImage;
import razepl.dev.social365.images.entities.image.interfaces.ImagesMapper;
import razepl.dev.social365.images.entities.image.post.PostImage;

@Component
public class ImagesMapperImpl implements ImagesMapper {

    private static final String IMAGE_VOLUME_PATH = System.getenv("IMAGE_VOLUME_PATH");
    public static final String IMAGES = "/images";

    @Override
    public final ImageResponse toImageResponse(Image image) {
        if (image == null) {
            return null;
        }
        return ImageResponse
                .builder()
                .imageId(image.getImageId())
                .username(image.getUsername())
                .imagePath(getNginxImagePath(image.getImagePath()))
                .build();
    }

    @Override
    public final PostImageResponse toPostImageResponse(PostImage postImage) {
        if (postImage == null) {
            return null;
        }
        return PostImageResponse
                .builder()
                .imageId(postImage.getImageId())
                .postId(postImage.getPostId())
                .imagePath(getNginxImagePath(postImage.getImagePath()))
                .username(postImage.getUsername())
                .build();
    }

    @Override
    public final CommentImageResponse toCommentResponse(CommentImage savedImage) {
        if (savedImage == null) {
            return null;
        }
        return CommentImageResponse
                .builder()
                .commentId(savedImage.getCommentId())
                .imageId(savedImage.getImageId())
                .username(savedImage.getUsername())
                .imagePath(getNginxImagePath(savedImage.getImagePath()))
                .build();
    }

    private String getNginxImagePath(String imagePath) {
        return imagePath.replace(IMAGE_VOLUME_PATH, IMAGES);
    }

}
