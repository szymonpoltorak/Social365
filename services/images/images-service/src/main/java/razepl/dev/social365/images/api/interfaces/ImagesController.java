package razepl.dev.social365.images.api.interfaces;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.multipart.MultipartFile;
import razepl.dev.social365.images.api.data.CommentImageResponse;
import razepl.dev.social365.images.api.data.ImageResponse;
import razepl.dev.social365.images.api.data.PostImageResponse;
import razepl.dev.social365.images.config.auth.User;
import razepl.dev.social365.images.entities.image.ImageType;
import razepl.dev.social365.images.utils.pagination.SocialPage;
import razepl.dev.social365.images.utils.pagination.SocialPageImpl;

import java.util.List;

@Tag(name = "ImagesController", description = "Operations pertaining to images in Social365")
public interface ImagesController {

    @Operation(summary = "Upload an image", description = "Uploads an image and returns the image response", responses = {
            @ApiResponse(responseCode = "200", description = "Image uploaded successfully", content = @Content(schema = @Schema(implementation = ImageResponse.class)))
    })
    ImageResponse uploadImage(
            @Parameter(description = "User uploading the image", required = true) User user,
            @Parameter(description = "Type of a image", required = true) ImageType imageType,
            @Parameter(description = "Image file to upload", required = true) MultipartFile image
    );

    @Operation(summary = "Get user uploaded images", description = "Retrieves images uploaded by a user", responses = {
            @ApiResponse(responseCode = "200", description = "Images retrieved successfully", content = @Content(schema = @Schema(implementation = SocialPageImpl.class)))
    })
    SocialPage<PostImageResponse> getUserUploadedImages(
            @Parameter(description = "Username of the user", required = true) String username,
            @Parameter(description = "Page number", required = true) int page,
            @Parameter(description = "Page size", required = true) int pageSize
    );

    @Operation(summary = "Upload a comment image", description = "Uploads an image for a comment and returns the image response", responses = {
            @ApiResponse(responseCode = "200", description = "Comment image uploaded successfully", content = @Content(schema = @Schema(implementation = CommentImageResponse.class)))
    })
    CommentImageResponse uploadCommentImage(
            @Parameter(description = "Comment ID", required = true) String commentId,
            @Parameter(description = "Id of post connected to comment", required = true) String postId,
            @Parameter(description = "User uploading the image", required = true) User user,
            @Parameter(description = "Image file to upload", required = true) MultipartFile image
    );

    @Operation(summary = "Upload a post image", description = "Uploads an image for a post and returns the image response", responses = {
            @ApiResponse(responseCode = "200", description = "Post image uploaded successfully", content = @Content(schema = @Schema(implementation = PostImageResponse.class)))
    })
    PostImageResponse uploadPostImage(
            @Parameter(description = "Post ID", required = true) String postId,
            @Parameter(description = "User uploading the image", required = true) User user,
            @Parameter(description = "Image file to upload", required = true) MultipartFile image
    );

    @Operation(summary = "Get a comment image", description = "Retrieves an image for a comment", responses = {
            @ApiResponse(responseCode = "200", description = "Comment image retrieved successfully", content = @Content(schema = @Schema(implementation = CommentImageResponse.class)))
    })
    CommentImageResponse getCommentImage(
            @Parameter(description = "Comment ID", required = true) String commentId
    );

    @Operation(summary = "Get post images", description = "Retrieves images for a post", responses = {
            @ApiResponse(responseCode = "200", description = "Post images retrieved successfully", content = @Content(schema = @Schema(implementation = PostImageResponse.class)))
    })
    List<PostImageResponse> getPostImages(
            @Parameter(description = "Post ID", required = true) String postId
    );

    @Operation(summary = "Get image path", description = "Retrieves the path of an image", responses = {
            @ApiResponse(responseCode = "200", description = "Image path retrieved successfully", content = @Content(schema = @Schema(implementation = ImageResponse.class)))
    })
    ImageResponse getImagePath(
            @Parameter(description = "Image ID", required = true) long imageId
    );

    @Operation(summary = "Update an image", description = "Updates an image and returns the updated image response", responses = {
            @ApiResponse(responseCode = "200", description = "Image updated successfully", content = @Content(schema = @Schema(implementation = ImageResponse.class)))
    })
    ImageResponse updateImage(
            @Parameter(description = "Image URL", required = true) String imageUrl,
            @Parameter(description = "Type of an image", required = true) ImageType imageType,
            @Parameter(description = "User updating the image", required = true) User user,
            @Parameter(description = "New image file", required = true) MultipartFile image
    );

    @Operation(summary = "Delete an image", description = "Deletes an image and returns the image response", responses = {
            @ApiResponse(responseCode = "200", description = "Image deleted successfully", content = @Content(schema = @Schema(implementation = ImageResponse.class)))
    })
    ImageResponse deleteImage(
            @Parameter(description = "Image ID", required = true) long imageId,
            @Parameter(description = "User deleting the image", required = true) User user
    );

    @Operation(summary = "Delete an image by URL", description = "Deletes an image by URL and returns the image response", responses = {
            @ApiResponse(responseCode = "200", description = "Image deleted successfully", content = @Content(schema = @Schema(implementation = ImageResponse.class)))
    })
    ImageResponse deleteImageByImageUrl(
            @Parameter(description = "Image URL", required = true) String imageUrl,
            @Parameter(description = "User deleting the image", required = true) User user
    );

    @Operation(summary = "Delete a post image by URL", description = "Deletes a post image by URL and returns the image response", responses = {
            @ApiResponse(responseCode = "200", description = "Post image deleted successfully", content = @Content(schema = @Schema(implementation = PostImageResponse.class)))
    })
    PostImageResponse deletePostImageByImageUrl(
            @Parameter(description = "Image URL", required = true) String imageUrl,
            @Parameter(description = "User deleting the image", required = true) User user
    );

    @Operation(summary = "Delete a comment image by ID", description = "Deletes a comment image by ID and returns the image response", responses = {
            @ApiResponse(responseCode = "200", description = "Comment image deleted successfully", content = @Content(schema = @Schema(implementation = CommentImageResponse.class)))
    })
    CommentImageResponse deleteCommentImageById(
            @Parameter(description = "Comment ID", required = true) String commentId,
            @Parameter(description = "User deleting the image", required = true) User user
    );

    @Operation(summary = "Delete images by post ID", description = "Deletes images by post ID and returns the image response", responses = {
            @ApiResponse(responseCode = "200", description = "Images deleted successfully", content = @Content(schema = @Schema(implementation = PostImageResponse.class)))
    })
    List<PostImageResponse> deleteImagesByPostId(
            @Parameter(description = "Post ID", required = true) String postId,
            @Parameter(description = "User deleting the images", required = true) User user
    );

    @Operation(summary = "Get profile image by profile ID", description = "Retrieves the profile image by profile ID", responses = {
            @ApiResponse(responseCode = "200", description = "Profile image retrieved successfully", content = @Content(schema = @Schema(implementation = ImageResponse.class)))
    })
    ImageResponse getProfileImageByProfileId(@Parameter(description = "Id of a profile", required = true) String profileId);

}
