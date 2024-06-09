package razepl.dev.social365.images.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    @PostMapping(value = ImagesMappings.UPLOAD_IMAGE_MAPPING)
    public final ImageResponse uploadImage(@RequestParam(Params.USERNAME) String username,
                                           @RequestBody MultipartFile image) {
        return imagesService.uploadImage(username, image);
    }

    @Override
    @PostMapping(value = ImagesMappings.UPLOAD_COMMENT_IMAGE_MAPPING)
    public final CommentImageResponse uploadCommentImage(@RequestParam(Params.COMMENT_ID) String commentId,
                                                         @RequestParam(Params.USERNAME) String username,
                                                         @RequestBody MultipartFile image) {
        return imagesService.uploadCommentImage(commentId, username, image);
    }

    @Override
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
}
