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
import razepl.dev.social365.images.exceptions.ImageNotFoundException;

import java.nio.file.Path;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImagesServiceImpl implements ImagesService {

    private static final String IMAGE_VOLUME_PATH = System.getenv("IMAGE_VOLUME_PATH");
    private static final String IMAGE_NOT_FOUND = "Image not found";
    private static final String IMAGE_FOUND = "Image found: {}";
    private final ImagesRepository imagesRepository;
    private final FileManagementService fileManagementService;
    private final ImagesMapper imagesMapper;

    @Override
    public final ImageResponse uploadImage(String username, MultipartFile image) {
        log.info("Uploading image for user: {}", username);

        String imagePath = Path.of(IMAGE_VOLUME_PATH, username, image.getOriginalFilename()).toString();

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
        log.info("Getting image path for imageId: {}", imageId);

        Image image = imagesRepository.findImageByImageId(imageId)
                .orElseThrow(() -> new ImageNotFoundException(IMAGE_NOT_FOUND));

        log.info(IMAGE_FOUND, image);

        return imagesMapper.toImageResponse(image);
    }

    @Override
    public final ImageResponse deleteImage(long imageId) {
        log.info("Deleting image with imageId: {}", imageId);

        Image image = imagesRepository.findImageByImageId(imageId)
                .orElseThrow(() -> new ImageNotFoundException(IMAGE_NOT_FOUND));

        log.info(IMAGE_FOUND, image);

        imagesRepository.delete(image);

        fileManagementService.deleteFile(image.getImagePath());

        return imagesMapper.toImageResponse(image);
    }
}
