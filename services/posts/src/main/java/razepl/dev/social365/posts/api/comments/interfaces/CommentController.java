package razepl.dev.social365.posts.api.comments.interfaces;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import razepl.dev.social365.posts.api.comments.data.CommentAddRequest;
import razepl.dev.social365.posts.api.comments.data.CommentEditRequest;
import razepl.dev.social365.posts.api.comments.data.CommentResponse;
import razepl.dev.social365.posts.config.auth.User;
import razepl.dev.social365.posts.entities.comment.data.CommentKeyResponse;
import razepl.dev.social365.posts.utils.pagination.interfaces.SocialPage;

@Tag(name = "CommentController", description = "API for managing comments")
public interface CommentController {

    @Operation(summary = "Get comments for a post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved comments"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters supplied", content = @Content)
    })
    SocialPage<CommentResponse> getCommentsForPost(@Parameter(description = "Post ID") @RequestParam String postId,
                                                   @Parameter(description = "Authenticated user") User user,
                                                   @Parameter(description = "Page size") @RequestParam int pageSize,
                                                   @Parameter(description = "Paging state", required = false) @RequestParam(required = false) String pagingState);

    @Operation(summary = "Add a comment to a post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully added the comment"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters supplied", content = @Content)
    })
    CommentResponse addCommentToPost(@Parameter(description = "Authenticated user") User user,
                                     @Parameter(description = "Comment request") @RequestBody CommentAddRequest commentRequest);

    @Operation(summary = "Edit a comment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully edited the comment"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters supplied", content = @Content)
    })
    CommentResponse editComment(@Parameter(description = "Authenticated user") User user,
                                @Parameter(description = "Edit comment request") @RequestBody CommentEditRequest commentEditRequest);

    @Operation(summary = "Delete a comment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted the comment"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters supplied", content = @Content)
    })
    CommentResponse deleteComment(@Parameter(description = "Authenticated user") User user,
                                  @Parameter(description = "Comment key") @RequestBody CommentKeyResponse commentKey);

    @Operation(summary = "Update like comment count")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated like comment count"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters supplied", content = @Content)
    })
    CommentResponse updateLikeCommentCount(@Parameter(description = "Authenticated user") User user,
                                           @Parameter(description = "Comment key") @RequestBody CommentKeyResponse commentKey);
}
