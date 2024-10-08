package razepl.dev.social365.posts.api.comments;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import razepl.dev.social365.posts.api.comments.constants.CommentMappings;
import razepl.dev.social365.posts.api.comments.data.CommentAddRequest;
import razepl.dev.social365.posts.api.comments.data.CommentEditRequest;
import razepl.dev.social365.posts.api.comments.data.CommentResponse;
import razepl.dev.social365.posts.api.comments.interfaces.CommentController;
import razepl.dev.social365.posts.api.comments.interfaces.CommentService;
import razepl.dev.social365.posts.api.constants.Params;
import razepl.dev.social365.posts.config.auth.AuthUser;
import razepl.dev.social365.posts.config.auth.User;
import razepl.dev.social365.posts.entities.comment.data.CommentKeyResponse;
import razepl.dev.social365.posts.utils.pagination.data.PageInfo;
import razepl.dev.social365.posts.utils.pagination.interfaces.SocialPage;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = CommentMappings.COMMENT_MAPPING)
public class CommentControllerImpl implements CommentController {

    private final CommentService commentService;

    @Override
    @GetMapping(value = CommentMappings.GET_COMMENTS_FOR_POST)
    public final SocialPage<CommentResponse> getCommentsForPost(@RequestParam(Params.POST_ID) String postId,
                                                                @AuthUser User user,
                                                                @RequestParam(Params.PAGE_SIZE) int pageSize,
                                                                @RequestParam(value = Params.PAGING_STATE, required = false) String pagingState) {
        return commentService.getCommentsForPost(postId, user.profileId(), PageInfo.of(pageSize, pagingState));
    }

    @Override
    @PostMapping(value = CommentMappings.ADD_COMMENT_TO_POST)
    public final CommentResponse addCommentToPost(@AuthUser User user, @RequestBody CommentAddRequest commentRequest) {
        return commentService.addCommentToPost(user, commentRequest);
    }

    @Override
    @PutMapping(value = CommentMappings.EDIT_COMMENT)
    public final CommentResponse editComment(@AuthUser User user, @RequestBody CommentEditRequest commentEditRequest) {
        return commentService.editComment(user, commentEditRequest);
    }

    @Override
    @DeleteMapping(value = CommentMappings.DELETE_COMMENT)
    public final CommentResponse deleteComment(@AuthUser User user, @RequestBody CommentKeyResponse commentKey) {
        return commentService.deleteComment(user, commentKey);
    }

    @Override
    @PutMapping(value = CommentMappings.UPDATE_LIKE_COMMENT_COUNT)
    public final CommentResponse updateLikeCommentCount(@AuthUser User user, @RequestBody CommentKeyResponse commentKey) {
        return commentService.updateLikeCommentCount(user, commentKey);
    }

}
