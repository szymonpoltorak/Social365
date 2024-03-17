package razepl.dev.social365.images.entities.image.interfaces;

import razepl.dev.social365.images.api.data.ImageResponse;
import razepl.dev.social365.images.entities.image.Image;

@FunctionalInterface
public interface ImagesMapper {
    ImageResponse toImageResponse(Image image);
}
