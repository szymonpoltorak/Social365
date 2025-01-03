package razepl.dev.social365.images.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import razepl.dev.social365.images.api.data.CommentImageResponse;
import razepl.dev.social365.images.api.data.ImageResponse;
import razepl.dev.social365.images.api.data.PostImageResponse;
import razepl.dev.social365.images.api.interfaces.FileManagementService;
import razepl.dev.social365.images.api.interfaces.ImagesService;
import razepl.dev.social365.images.config.auth.User;
import razepl.dev.social365.images.entities.image.Image;
import razepl.dev.social365.images.entities.image.ImageType;
import razepl.dev.social365.images.entities.image.ImagesMapperImpl;
import razepl.dev.social365.images.entities.image.comment.CommentImage;
import razepl.dev.social365.images.entities.image.comment.interfaces.CommentImageRepository;
import razepl.dev.social365.images.entities.image.interfaces.ImagesMapper;
import razepl.dev.social365.images.entities.image.interfaces.ImagesRepository;
import razepl.dev.social365.images.entities.image.post.PostImage;
import razepl.dev.social365.images.entities.image.post.interfaces.PostImagesRepository;
import razepl.dev.social365.images.exceptions.ImageNotFoundException;
import razepl.dev.social365.images.exceptions.UserNotImageAuthorException;
import razepl.dev.social365.images.utils.pagination.SocialPage;
import razepl.dev.social365.images.utils.pagination.SocialPageImpl;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImagesServiceImpl implements ImagesService {

    static final String IMAGE_VOLUME_PATH = System.getenv("IMAGE_VOLUME_PATH");
    private static final String IMAGE_NOT_FOUND = "Image not found";

    private final ImagesRepository imagesRepository;
    private final FileManagementService fileManagementService;
    private final ImagesMapper imagesMapper;
    private final PostImagesRepository postImagesRepository;
    private final CommentImageRepository commentImageRepository;

    @Override
    public ImageResponse uploadImage(User user, ImageType imageType, MultipartFile image) {
        log.info("Uploading image for user: {}", user);

        String newFilename = String.format("%s_%s", UUID.randomUUID(), UUID.randomUUID());
        Path imagePath = Path.of(IMAGE_VOLUME_PATH, user.username(), newFilename);

        Image imageEntity = Image
                .builder()
                .username(user.username())
                .imageType(imageType)
                .profileId(user.profileId())
                .imagePath(imagePath.toString())
                .build();
        Image savedImage = imagesRepository.save(imageEntity);

        log.info("Image saved: {}", savedImage);

        saveFile(imagePath, image);

        return imagesMapper.toImageResponse(savedImage);
    }

    @Override
    public SocialPage<PostImageResponse> getUserUploadedImages(String username, Pageable pageable) {
        log.info("Getting user uploaded images for user: {}", username);

        Page<PostImage> postImages = postImagesRepository.findPostImagesByUsername(username, pageable);

        log.info("User uploaded images found: {}", postImages.getNumberOfElements());

        if (postImages.isEmpty()) {
            return SocialPageImpl.empty();
        }
        return SocialPageImpl.from(postImages.map(imagesMapper::toPostImageResponse));
    }

    @Override
    public CommentImageResponse uploadCommentImage(String commentId, String username, String postId, MultipartFile image) {
        log.info("Uploading comment image for user: {}", username);

        String newFilename = String.format("%s_%s", commentId, image.getOriginalFilename());
        Path imagePath = Path.of(IMAGE_VOLUME_PATH, username, newFilename);

        CommentImage imageEntity = CommentImage
                .builder()
                .username(username)
                .imagePath(imagePath.toString())
                .postId(postId)
                .commentId(commentId)
                .build();

        CommentImage savedImage = commentImageRepository.save(imageEntity);

        log.info("Comment image saved: {}", savedImage);

        saveFile(imagePath, image);

        return imagesMapper.toCommentResponse(savedImage);
    }

    @Override
    public PostImageResponse uploadPostImage(String postId, String username, MultipartFile image) {
        log.info("Uploading post image for user: {}", username);

        String newFilename = String.format("%s_%s", postId, image.getOriginalFilename());
        Path imagePath = Path.of(IMAGE_VOLUME_PATH, username, newFilename);

        PostImage imageEntity = PostImage
                .builder()
                .username(username)
                .imagePath(imagePath.toString())
                .postId(postId)
                .build();

        PostImage savedImage = postImagesRepository.save(imageEntity);

        log.info("Post image saved: {}", savedImage);

        saveFile(imagePath, image);

        return imagesMapper.toPostImageResponse(savedImage);
    }

    @Override
    public CommentImageResponse getCommentImage(String commentId) {
        log.info("Getting comment image for commentId: {}", commentId);

        CommentImage commentImage = commentImageRepository.findCommentImageByCommentId(commentId)
                .orElseThrow(() -> new ImageNotFoundException(IMAGE_NOT_FOUND));

        log.info("Comment image found: {}", commentImage);

        return imagesMapper.toCommentResponse(commentImage);
    }

    @Override
    public List<PostImageResponse> getPostImages(String postId) {
        log.info("Getting post images for postId: {}", postId);

        List<PostImage> postImages = postImagesRepository.findPostImagesByPostId(postId);

        log.info("Post images found: {}", postImages);

        return postImages
                .stream()
                .map(imagesMapper::toPostImageResponse)
                .toList();
    }

    @Override
    public ImageResponse getImagePath(long imageId) {
        log.info("Getting image path for imageId: {}", imageId);

        Image image = getImageFromRepository(imageId);

        return imagesMapper.toImageResponse(image);
    }

    @Override
    @Transactional
    public ImageResponse updateImage(String imageUrl, MultipartFile image, User user, ImageType imageType) {
        log.info("Updating image with imagePath: {}", imageUrl);

        Path imageUrlPath = Path.of(IMAGE_VOLUME_PATH.replace(ImagesMapperImpl.IMAGES, ""), imageUrl);

        Image imageEntity = imagesRepository.findImageByImagePath(imageUrlPath.toString())
                .orElseThrow(() -> new ImageNotFoundException(IMAGE_NOT_FOUND));

        log.info("Found image: {}", imageEntity);

        log.info("Deleting old image: {}", imageUrlPath);

        fileManagementService.deleteFile(imageUrlPath);

        String newFileName = String
                .format("%s_%s", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME), image.getOriginalFilename());
        String newFilePath = Path.of(IMAGE_VOLUME_PATH, newFileName).toString();

        log.info("Saving new image: {}", newFilePath);

        imageEntity.setImageType(imageType);
        imageEntity.setImagePath(newFilePath);

        imageEntity = imagesRepository.save(imageEntity);

        saveFile(Path.of(newFilePath), image);

        return imagesMapper.toImageResponse(imageEntity);
    }

    @Override
    public ImageResponse deleteImage(long imageId, User user) {
        log.info("Deleting image with imageId: {}", imageId);

        Image image = getImageFromRepository(imageId);

        imagesRepository.delete(image);

        fileManagementService.deleteFile(Path.of(image.getImagePath()));

        return imagesMapper.toImageResponse(image);
    }

    @Override
    public ImageResponse deleteImageByImageUrl(String imageUrl, User user) {
        log.info("Deleting image with imageUrl: {}", imageUrl);

        Image image = imagesRepository.findImageByImagePath(imageUrl)
                .orElseThrow(() -> new ImageNotFoundException(IMAGE_NOT_FOUND));

        log.info("Image found by url: {}", image);

        if (!image.getUsername().equals(user.username())) {
            throw new UserNotImageAuthorException(user.username());
        }
        imagesRepository.delete(image);

        fileManagementService.deleteFile(Path.of(image.getImagePath()));

        log.info("Image deleted");

        return imagesMapper.toImageResponse(image);
    }

    @Override
    @Transactional
    public PostImageResponse deletePostImageByImageUrl(String imageUrl, User user) {
        log.info("Deleting post image with imageUrl: {}", imageUrl);

        PostImage postImage = postImagesRepository.findPostImageByImagePath(imageUrl)
                .orElseThrow(() -> new ImageNotFoundException(IMAGE_NOT_FOUND));

        log.info("Post image found by url: {}", postImage);

        if (postImage.getUsername().equals(user.username())) {
            throw new UserNotImageAuthorException(user.username());
        }
        postImagesRepository.delete(postImage);

        fileManagementService.deleteFile(Path.of(postImage.getImagePath()));

        log.info("Post image deleted");

        return imagesMapper.toPostImageResponse(postImage);
    }

    @Override
    @Transactional
    public CommentImageResponse deleteCommentImageById(String commentId, User user) {
        log.info("Deleting comment image with commentId: {}", commentId);

        CommentImage commentImage = commentImageRepository.findCommentImageByCommentId(commentId)
                .orElseThrow(() -> new ImageNotFoundException(IMAGE_NOT_FOUND));

        log.info("Comment image found: {}", commentImage);

        if (!commentImage.getUsername().equals(user.username())) {
            throw new UserNotImageAuthorException(user.username());
        }
        commentImageRepository.delete(commentImage);

        fileManagementService.deleteFile(Path.of(commentImage.getImagePath()));

        log.info("Comment image deleted");

        return imagesMapper.toCommentResponse(commentImage);
    }

    @Override
    @Transactional
    public List<PostImageResponse> deleteImagesByPostId(String postId, User user) {
        log.info("Deleting post images with postId: {}", postId);

        List<PostImage> postImages = postImagesRepository.findPostImagesByPostId(postId);

        if (postImages.stream().noneMatch(postImage -> postImage.getUsername().equals(user.username()))) {
            throw new UserNotImageAuthorException(user.username());
        }
        log.info("Deleting images from database...");

        postImagesRepository.deleteAllByPostId(postId);

        log.info("Deleting images from filesystem...");

        postImages.forEach(postImage -> fileManagementService.deleteFile(Path.of(postImage.getImagePath())));

        log.info("Post images deleted");

        return postImages
                .stream()
                .map(imagesMapper::toPostImageResponse)
                .toList();
    }

    @Override
    public ImageResponse getProfileImageByProfileId(String profileId) {
        log.info("Getting profile image for profileId: {}", profileId);

        Image image = imagesRepository.findImageByProfileIdAndImageType(profileId, ImageType.PROFILE_IMAGE)
                .orElseThrow(() -> new ImageNotFoundException(IMAGE_NOT_FOUND));

        log.info("Profile image found: {}", image);

        return imagesMapper.toImageResponse(image);
    }

    private void saveFile(Path imagePath, MultipartFile image) {
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
