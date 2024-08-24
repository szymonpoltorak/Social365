package razepl.dev.social365.posts.api.comments.replies.interfaces;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import razepl.dev.social365.posts.api.comments.data.CommentResponse;
import razepl.dev.social365.posts.api.comments.replies.data.ReplyAddRequest;
import razepl.dev.social365.posts.api.comments.replies.data.ReplyEditRequest;
import razepl.dev.social365.posts.config.User;
import razepl.dev.social365.posts.entities.comment.reply.data.ReplyKeyResponse;
import razepl.dev.social365.posts.utils.pagination.interfaces.SocialPage;

@Tag(name = "RepliesController", description = "API for managing comment replies")
public interface RepliesController {

    @Operation(summary = "Get replies for a comment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved replies"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters supplied", content = @Content)
    })
    SocialPage<CommentResponse> getRepliesForComment(@Parameter(description = "Comment ID") @RequestParam String commentId,
                                                     @Parameter(description = "Authenticated user") User user,
                                                     @Parameter(description = "Page size") @RequestParam int pageSize,
                                                     @Parameter(description = "Paging state", required = false) @RequestParam(required = false) String pagingState);

    @Operation(summary = "Add a reply to a comment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully added the reply"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters supplied", content = @Content)
    })
    CommentResponse addReplyToComment(@Parameter(description = "Authenticated user") User user,
                                      @Parameter(description = "Reply request") @RequestBody ReplyAddRequest commentRequest);

    @Operation(summary = "Edit a reply")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully edited the reply"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters supplied", content = @Content)
    })
    CommentResponse editReplyComment(@Parameter(description = "Authenticated user") User user,
                                     @Parameter(description = "Edit reply request") @RequestBody ReplyEditRequest commentRequest);

    @Operation(summary = "Delete a reply")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted the reply"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters supplied", content = @Content)
    })
    CommentResponse deleteReplyComment(@Parameter(description = "Authenticated user") User user,
                                       @Parameter(description = "Reply key") @RequestBody ReplyKeyResponse replyKey);

    @Operation(summary = "Update like comment count for a reply")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated like comment count"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters supplied", content = @Content)
    })
    CommentResponse updateLikeCommentCount(@Parameter(description = "Authenticated user") User user,
                                           @Parameter(description = "Reply key") @RequestBody ReplyKeyResponse replyKey);
}
