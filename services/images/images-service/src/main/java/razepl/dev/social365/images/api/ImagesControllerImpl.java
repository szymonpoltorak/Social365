package razepl.dev.social365.images.api;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import razepl.dev.social365.images.api.constants.ImagesMappings;
import razepl.dev.social365.images.api.constants.Params;
import razepl.dev.social365.images.api.data.CommentImageResponse;
import razepl.dev.social365.images.api.data.ImageResponse;
import razepl.dev.social365.images.api.data.PostImageResponse;
import razepl.dev.social365.images.api.interfaces.ImagesController;
import razepl.dev.social365.images.api.interfaces.ImagesService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = ImagesMappings.IMAGES_MAPPING)
public class ImagesControllerImpl implements ImagesController {

    private final ImagesService imagesService;

    @Override
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = ImagesMappings.UPLOAD_IMAGE_MAPPING)
    public final ImageResponse uploadImage(@RequestParam(Params.USERNAME) String username,
                                           @RequestBody MultipartFile image) {
        return imagesService.uploadImage(username, image);
    }

    @Override
    @GetMapping(value = ImagesMappings.GET_USER_UPLOADED_IMAGES_MAPPING)
    public final Page<PostImageResponse> getUserUploadedImages(@RequestParam(Params.USERNAME) String username,
                                                               @RequestParam(Params.PAGE_NUMBER) int pageNumber,
                                                               @RequestParam(Params.PAGE_SIZE) int pageSize) {
        return imagesService.getUserUploadedImages(username, PageRequest.of(pageNumber, pageSize));
    }

    @Override
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = ImagesMappings.UPLOAD_COMMENT_IMAGE_MAPPING)
    public final CommentImageResponse uploadCommentImage(@RequestParam(Params.COMMENT_ID) String commentId,
                                                         @RequestParam(Params.USERNAME) String username,
                                                         @RequestBody MultipartFile image) {
        return imagesService.uploadCommentImage(commentId, username, image);
    }

    @Override
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = ImagesMappings.UPLOAD_POST_IMAGE_MAPPING)
    public final PostImageResponse uploadPostImage(@RequestParam(Params.POST_ID) String postId,
                                                   @RequestParam(Params.USERNAME) String username,
                                                   @RequestBody MultipartFile image) {
        return imagesService.uploadPostImage(postId, username, image);
    }

    @Override
    @GetMapping(value = ImagesMappings.GET_COMMENT_IMAGE_MAPPING)
    public final CommentImageResponse getCommentImage(@RequestParam(Params.COMMENT_ID) String commentId) {
        return imagesService.getCommentImage(commentId);
    }

    @Override
    @GetMapping(value = ImagesMappings.GET_POST_IMAGES_MAPPING)
    public final List<PostImageResponse> getPostImages(@RequestParam(Params.POST_ID) String postId) {
        return imagesService.getPostImages(postId);
    }

    @Override
    @GetMapping(value = ImagesMappings.GET_IMAGE_MAPPING)
    public final ImageResponse getImagePath(@RequestParam(Params.IMAGE_ID) long imageId) {
        return imagesService.getImagePath(imageId);
    }

    @Override
    @PutMapping(value = ImagesMappings.UPDATE_IMAGE_MAPPING)
    public final ImageResponse updateImage(@RequestParam(Params.IMAGE_ID) long imageId,
                                           @RequestBody MultipartFile image) {
        return imagesService.updateImage(imageId, image);
    }

    @Override
    @DeleteMapping(value = ImagesMappings.DELETE_IMAGE_MAPPING)
    public final ImageResponse deleteImage(@RequestParam(Params.IMAGE_ID) long imageId) {
        return imagesService.deleteImage(imageId);
    }

    @Override
    @DeleteMapping(value = ImagesMappings.DELETE_IMAGE_BY_URL_MAPPING)
    public final ImageResponse deleteImageByImageUrl(@RequestParam(Params.IMAGE_URL) String imageUrl) {
        return imagesService.deleteImageByImageUrl(imageUrl);
    }

    @Override
    @DeleteMapping(value = ImagesMappings.DELETE_POST_IMAGE_BY_URL_MAPPING)
    public final PostImageResponse deletePostImageByImageUrl(@RequestParam(Params.IMAGE_URL) String imageUrl) {
        return imagesService.deletePostImageByImageUrl(imageUrl);
    }

    @Override
    @DeleteMapping(value = ImagesMappings.DELETE_COMMENT_IMAGE_BY_ID_MAPPING)
    public final CommentImageResponse deleteCommentImageById(@RequestParam(Params.COMMENT_ID) String commentId) {
        return imagesService.deleteCommentImageById(commentId);
    }

    @Override
    @DeleteMapping(value = ImagesMappings.DELETE_IMAGES_BY_POST_ID_MAPPING)
    public final List<PostImageResponse> deleteImagesByPostId(@RequestParam(Params.POST_ID) String postId) {
        return imagesService.deleteImagesByPostId(postId);
    }
}
