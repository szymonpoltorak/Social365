package razepl.dev.social365.posts.api.posts.interfaces;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import razepl.dev.social365.posts.api.posts.data.EditPostRequest;
import razepl.dev.social365.posts.config.User;
import razepl.dev.social365.posts.utils.pagination.interfaces.SocialPage;

@Tag(name = "PostController", description = "API for managing posts")
public interface PostController {

    @Operation(summary = "Get user's post count")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved post count"),
            @ApiResponse(responseCode = "400", description = "Invalid profile ID supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Profile not found", content = @Content)
    })
    int getUsersPostCount(@Parameter(description = "Profile ID of the user") @RequestParam String profileId);

    @Operation(summary = "Get posts on a page")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved posts"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters supplied", content = @Content)
    })
    SocialPage<PostData> getPostsOnPage(@Parameter(description = "Authenticated user") User user,
                                        @Parameter(description = "Page number") @RequestParam int pageNumber,
                                        @Parameter(description = "Page size") @RequestParam int pageSize,
                                        @Parameter(description = "Paging state", required = false) @RequestParam(required = false) String pagingState);

    @Operation(summary = "Get user's posts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved user's posts"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters supplied", content = @Content)
    })
    SocialPage<PostData> getUsersPosts(@Parameter(description = "Profile ID of the user") @RequestParam String profileId,
                                       @Parameter(description = "Page number") @RequestParam int pageNumber,
                                       @Parameter(description = "Page size") @RequestParam int pageSize,
                                       @Parameter(description = "Paging state", required = false) @RequestParam(required = false) String pagingState);

    @Operation(summary = "Update like post count")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated like post count"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters supplied", content = @Content)
    })
    PostData updateLikePostCount(@Parameter(description = "Authenticated user") User user,
                                 @Parameter(description = "Author ID") @RequestParam String authorId,
                                 @Parameter(description = "Post ID") @RequestParam String postId);

    @Operation(summary = "Update notification status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated notification status"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters supplied", content = @Content)
    })
    PostData updateNotificationStatus(@Parameter(description = "Authenticated user") User user,
                                      @Parameter(description = "Author ID") @RequestParam String authorId,
                                      @Parameter(description = "Post ID") @RequestParam String postId);

    @Operation(summary = "Update bookmark status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated bookmark status"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters supplied", content = @Content)
    })
    PostData updateBookmarkStatus(@Parameter(description = "Authenticated user") User user,
                                  @Parameter(description = "Author ID") @RequestParam String authorId,
                                  @Parameter(description = "Post ID") @RequestParam String postId);

    @Operation(summary = "Share a post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully shared the post"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters supplied", content = @Content)
    })
    PostData sharePost(@Parameter(description = "Authenticated user") User user,
                       @Parameter(description = "Post ID") @RequestParam String postId,
                       @Parameter(description = "Author ID") @RequestParam String authorId,
                       @Parameter(description = "Content") @RequestParam String content);

    @Operation(summary = "Create a post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created the post"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters supplied", content = @Content)
    })
    PostData createPost(@Parameter(description = "Authenticated user") User user,
                        @Parameter(description = "Content") @RequestParam String content,
                        @Parameter(description = "Has attachments") @RequestParam boolean hasAttachments);

    @Operation(summary = "Edit a post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully edited the post"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters supplied", content = @Content)
    })
    PostData editPost(@Parameter(description = "Authenticated user") User user,
                      @Parameter(description = "Edit post request") @RequestBody EditPostRequest editPostRequest);

    @Operation(summary = "Delete a post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted the post"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters supplied", content = @Content)
    })
    PostData deletePost(@Parameter(description = "Authenticated user") User user,
                        @Parameter(description = "Author ID") @RequestParam String authorId,
                        @Parameter(description = "Post ID") @RequestParam String postId);
}