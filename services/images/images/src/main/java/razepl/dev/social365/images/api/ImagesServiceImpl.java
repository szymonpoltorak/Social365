package razepl.dev.social365.images.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import razepl.dev.social365.images.api.data.ImageResponse;
import razepl.dev.social365.images.api.interfaces.FileManagementService;
import razepl.dev.social365.images.api.interfaces.ImagesService;
import razepl.dev.social365.images.entities.image.Image;
import razepl.dev.social365.images.entities.image.interfaces.ImagesMapper;
import razepl.dev.social365.images.entities.image.interfaces.ImagesRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImagesServiceImpl implements ImagesService {
    private final ImagesRepository imagesRepository;
    private final FileManagementService fileManagementService;
    private final ImagesMapper imagesMapper;

    @Override
    public final ImageResponse uploadImage(String username, MultipartFile image) {
        log.info("Uploading image for user: {}", username);

        String imagePath = String.format("%s/%s", username, image.getOriginalFilename());

        Image imageEntity = Image
                .builder()
                .username(username)
                .imagePath(imagePath)
                .build();
        Image savedImage = imagesRepository.save(imageEntity);

        log.info("Image saved: {}", savedImage);

        fileManagementService.saveFile(imagePath, image);

        return imagesMapper.toImageResponse(savedImage);
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
