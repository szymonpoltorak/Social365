package razepl.dev.social365.images.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import razepl.dev.social365.images.api.data.ImageResponse;
import razepl.dev.social365.images.api.data.PostImageResponse;
import razepl.dev.social365.images.api.interfaces.FileManagementService;
import razepl.dev.social365.images.api.interfaces.ImagesService;
import razepl.dev.social365.images.entities.image.Image;
import razepl.dev.social365.images.entities.image.interfaces.ImagesMapper;
import razepl.dev.social365.images.entities.image.interfaces.ImagesRepository;
import razepl.dev.social365.images.entities.image.post.PostImage;
import razepl.dev.social365.images.entities.image.post.interfaces.PostImagesRepository;
import razepl.dev.social365.images.exceptions.ImageNotFoundException;

import java.nio.file.Path;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImagesServiceImpl implements ImagesService {

    private static final String IMAGE_VOLUME_PATH = System.getenv("IMAGE_VOLUME_PATH");
    private static final String IMAGE_NOT_FOUND = "Image not found";
    private final ImagesRepository imagesRepository;
    private final FileManagementService fileManagementService;
    private final ImagesMapper imagesMapper;
    private final PostImagesRepository postImagesRepository;

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
    public final PostImageResponse uploadPostImage(String postId, String username, MultipartFile image) {
        log.info("Uploading post image for user: {}", username);

        String imagePath = Path.of(IMAGE_VOLUME_PATH, username, image.getOriginalFilename()).toString();

        PostImage imageEntity = PostImage
                .builder()
                .username(username)
                .imagePath(imagePath)
                .postId(postId)
                .build();

        PostImage savedImage = postImagesRepository.save(imageEntity);

        log.info("Post image saved: {}", savedImage);

        fileManagementService.saveFile(imagePath, image);

        return imagesMapper.toPostImageResponse(savedImage);
    }

    @Override
    public final ImageResponse getImagePath(long imageId) {
        log.info("Getting image path for imageId: {}", imageId);

        Image image = getImageFromRepository(imageId);

        return imagesMapper.toImageResponse(image);
    }

    @Override
    public final ImageResponse updateImage(long imageId, MultipartFile image) {
        log.info("Updating image with imageId: {}", imageId);

        Image imageEntity = getImageFromRepository(imageId);

        log.info("Deleting old image: {}", imageEntity.getImagePath());

        fileManagementService.deleteFile(imageEntity.getImagePath());

        log.info("Saving new image: {}", imageEntity.getImagePath());

        fileManagementService.saveFile(imageEntity.getImagePath(), image);

        return imagesMapper.toImageResponse(imageEntity);
    }

    @Override
    public final ImageResponse deleteImage(long imageId) {
        log.info("Deleting image with imageId: {}", imageId);

        Image image = getImageFromRepository(imageId);

        imagesRepository.delete(image);

        fileManagementService.deleteFile(image.getImagePath());

        return imagesMapper.toImageResponse(image);
    }

    private Image getImageFromRepository(long imageId) {
        Image image = imagesRepository.findImageByImageId(imageId)
                .orElseThrow(() -> new ImageNotFoundException(IMAGE_NOT_FOUND));

        log.info("Image found: {}", image);

        return image;
    }
}
