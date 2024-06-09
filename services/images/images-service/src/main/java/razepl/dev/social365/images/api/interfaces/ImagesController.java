package razepl.dev.social365.images.api.interfaces;

import org.springframework.web.multipart.MultipartFile;
import razepl.dev.social365.images.api.data.CommentImageResponse;
import razepl.dev.social365.images.api.data.ImageResponse;
import razepl.dev.social365.images.api.data.PostImageResponse;

import java.util.List;

public interface ImagesController {
    ImageResponse uploadImage(String username, MultipartFile image);

    CommentImageResponse uploadCommentImage(String commentId, String username, MultipartFile image);

    PostImageResponse uploadPostImage(String postId, String username, MultipartFile image);

    CommentImageResponse getCommentImage(String commentId);

    List<PostImageResponse> getPostImages(String postId);

    ImageResponse getImagePath(long imageId);

    ImageResponse updateImage(long imageId, MultipartFile image);

    ImageResponse deleteImage(long imageId);
}
