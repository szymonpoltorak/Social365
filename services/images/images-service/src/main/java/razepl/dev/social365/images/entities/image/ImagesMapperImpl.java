package razepl.dev.social365.images.entities.image;

import org.springframework.stereotype.Component;
import razepl.dev.social365.images.api.data.ImageResponse;
import razepl.dev.social365.images.api.data.PostImageResponse;
import razepl.dev.social365.images.entities.image.interfaces.ImagesMapper;
import razepl.dev.social365.images.entities.image.post.PostImage;

@Component
public class ImagesMapperImpl implements ImagesMapper {

    @Override
    public final ImageResponse toImageResponse(Image image) {
        if (image == null) {
            return null;
        }
        return ImageResponse
                .builder()
                .imageId(image.getImageId())
                .username(image.getUsername())
                .imagePath(image.getImagePath())
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
                .imagePath(postImage.getImagePath())
                .username(postImage.getUsername())
                .build();
    }

}
