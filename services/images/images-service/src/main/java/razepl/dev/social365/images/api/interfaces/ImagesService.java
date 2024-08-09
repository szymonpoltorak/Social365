package razepl.dev.social365.images.api.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import razepl.dev.social365.images.api.data.CommentImageResponse;
import razepl.dev.social365.images.api.data.ImageResponse;
import razepl.dev.social365.images.api.data.PostImageResponse;
import razepl.dev.social365.images.config.User;

import java.util.List;

public interface ImagesService {

    ImageResponse uploadImage(String username, MultipartFile image);

    Page<PostImageResponse> getUserUploadedImages(String username, Pageable pageable);

    CommentImageResponse uploadCommentImage(String commentId, String username, MultipartFile image);

    PostImageResponse uploadPostImage(String postId, String username, MultipartFile image);

    CommentImageResponse getCommentImage(String commentId);

    List<PostImageResponse> getPostImages(String postId);

    ImageResponse getImagePath(long imageId);

    ImageResponse updateImage(String imageUrl, MultipartFile image, User user);

    ImageResponse deleteImage(long imageId, User user);

    ImageResponse deleteImageByImageUrl(String imageUrl, User user);

    PostImageResponse deletePostImageByImageUrl(String imageUrl, User user);

    CommentImageResponse deleteCommentImageById(String commentId, User user);

    List<PostImageResponse> deleteImagesByPostId(String postId, User user);

}
