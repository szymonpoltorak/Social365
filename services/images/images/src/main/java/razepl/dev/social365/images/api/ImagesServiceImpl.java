package razepl.dev.social365.images.api;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import razepl.dev.social365.images.api.data.ImageResponse;
import razepl.dev.social365.images.api.interfaces.ImagesService;

@Service
public class ImagesServiceImpl implements ImagesService {
    @Override
    public final ImageResponse uploadImage(String username, MultipartFile image) {
        return null;
    }

    @Override
    public final ImageResponse getImagePath(long imageId) {
        return null;
    }

    @Override
    public final ImageResponse deleteImage(long imageId) {
        return null;
    }
}
