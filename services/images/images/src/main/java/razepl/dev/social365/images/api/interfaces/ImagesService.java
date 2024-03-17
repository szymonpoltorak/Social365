package razepl.dev.social365.images.api.interfaces;

import org.springframework.web.multipart.MultipartFile;
import razepl.dev.social365.images.api.data.ImageResponse;

public interface ImagesService {
    ImageResponse uploadImage(String username, MultipartFile image);

    ImageResponse getImagePath(long imageId);

    ImageResponse updateImage(long imageId, MultipartFile image);

    ImageResponse deleteImage(long imageId);
}
