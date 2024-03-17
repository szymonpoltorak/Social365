package razepl.dev.social365.images.entities.image;

import org.springframework.stereotype.Component;
import razepl.dev.social365.images.api.data.ImageResponse;
import razepl.dev.social365.images.entities.image.interfaces.ImagesMapper;

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

}
