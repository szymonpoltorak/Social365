package razepl.dev.social365.images.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import razepl.dev.social365.images.api.data.CommentImageResponse;
import razepl.dev.social365.images.api.data.ImageResponse;
import razepl.dev.social365.images.api.data.PostImageResponse;
import razepl.dev.social365.images.api.interfaces.FileManagementService;
import razepl.dev.social365.images.api.interfaces.ImagesService;
import razepl.dev.social365.images.entities.image.Image;
import razepl.dev.social365.images.entities.image.comment.CommentImage;
import razepl.dev.social365.images.entities.image.comment.interfaces.CommentImageRepository;
import razepl.dev.social365.images.entities.image.interfaces.ImagesMapper;
import razepl.dev.social365.images.entities.image.interfaces.ImagesRepository;
import razepl.dev.social365.images.entities.image.post.PostImage;
import razepl.dev.social365.images.entities.image.post.interfaces.PostImagesRepository;
import razepl.dev.social365.images.exceptions.ImageNotFoundException;

import java.nio.file.Path;
import java.util.List;

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
    private final CommentImageRepository commentImageRepository;

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

        saveFile(imagePath, image);

        return imagesMapper.toImageResponse(savedImage);
    }

    @Override
    public final Page<PostImageResponse> getUserUploadedImages(String username, Pageable pageable) {
        log.info("Getting user uploaded images for user: {}", username);

        Page<PostImage> postImages = postImagesRepository.findPostImagesByUsername(username, pageable);

        log.info("User uploaded images found: {}", postImages.getNumberOfElements());

        return postImages.map(imagesMapper::toPostImageResponse);
    }

    @Override
    public final CommentImageResponse uploadCommentImage(String commentId, String username, MultipartFile image) {
        log.info("Uploading comment image for user: {}", username);

        String imagePath = Path.of(IMAGE_VOLUME_PATH, username, image.getOriginalFilename()).toString();

        CommentImage imageEntity = CommentImage
                .builder()
                .username(username)
                .imagePath(imagePath)
                .commentId(commentId)
                .build();

        CommentImage savedImage = commentImageRepository.save(imageEntity);

        log.info("Comment image saved: {}", savedImage);

        saveFile(imagePath, image);

        return imagesMapper.toCommentResponse(savedImage);
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

        saveFile(imagePath, image);

        return imagesMapper.toPostImageResponse(savedImage);
    }

    @Override
    public final CommentImageResponse getCommentImage(String commentId) {
        log.info("Getting comment image for commentId: {}", commentId);

        CommentImage commentImage = commentImageRepository.findCommentImageByCommentId(commentId)
                .orElseThrow(() -> new ImageNotFoundException(IMAGE_NOT_FOUND));

        log.info("Comment image found: {}", commentImage);

        return imagesMapper.toCommentResponse(commentImage);
    }

    @Override
    public final List<PostImageResponse> getPostImages(String postId) {
        log.info("Getting post images for postId: {}", postId);

        List<PostImage> postImages = postImagesRepository.findPostImagesByPostId(postId);

        log.info("Post images found: {}", postImages);

        return postImages
                .stream()
                .map(imagesMapper::toPostImageResponse)
                .toList();
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
        String imagePath = imageEntity.getImagePath();

        log.info("Deleting old image: {}", imagePath);

        fileManagementService.deleteFile(imagePath);

        log.info("Saving new image: {}", imagePath);

        saveFile(imagePath, image);

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

    private void saveFile(String imagePath, MultipartFile image) {
        fileManagementService.saveFile(imagePath, image);

        log.info("Image has been saved to file system.");
    }

    private Image getImageFromRepository(long imageId) {
        Image image = imagesRepository.findImageByImageId(imageId)
                .orElseThrow(() -> new ImageNotFoundException(IMAGE_NOT_FOUND));

        log.info("Image found: {}", image);

        return image;
    }
}
