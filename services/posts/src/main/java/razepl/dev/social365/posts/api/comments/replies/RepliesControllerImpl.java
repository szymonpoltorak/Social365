package razepl.dev.social365.posts.api.comments.replies;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import razepl.dev.social365.posts.api.comments.data.CommentResponse;
import razepl.dev.social365.posts.api.comments.replies.constants.ReplyMappings;
import razepl.dev.social365.posts.api.comments.replies.data.ReplyAddRequest;
import razepl.dev.social365.posts.api.comments.replies.data.ReplyEditRequest;
import razepl.dev.social365.posts.api.comments.replies.interfaces.RepliesController;
import razepl.dev.social365.posts.api.comments.replies.interfaces.RepliesService;
import razepl.dev.social365.posts.api.constants.Params;
import razepl.dev.social365.posts.config.auth.AuthUser;
import razepl.dev.social365.posts.config.auth.User;
import razepl.dev.social365.posts.entities.comment.reply.data.ReplyKeyResponse;
import razepl.dev.social365.posts.utils.pagination.data.PageInfo;
import razepl.dev.social365.posts.utils.pagination.interfaces.SocialPage;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = ReplyMappings.REPLIES_MAPPING)
public class RepliesControllerImpl implements RepliesController {

    private final RepliesService repliesService;

    @Override
    @GetMapping(value = ReplyMappings.GET_REPLIES_FOR_COMMENT)
    public final SocialPage<CommentResponse> getRepliesForComment(@RequestParam(Params.COMMENT_ID) String commentId,
                                                                  @AuthUser User user,
                                                                  @RequestParam(Params.PAGE_SIZE) int pageSize,
                                                                  @RequestParam(value = Params.PAGING_STATE, required = false) String pagingState) {
        return repliesService.getRepliesForComment(commentId, user.profileId(), PageInfo.of(pageSize, pagingState));
    }

    @Override
    @PostMapping(value = ReplyMappings.ADD_REPLY_TO_COMMENT)
    public final CommentResponse addReplyToComment(@AuthUser User user, @RequestBody ReplyAddRequest commentRequest) {
        return repliesService.addReplyToComment(user, commentRequest);
    }

    @Override
    @PutMapping(value = ReplyMappings.EDIT_REPLY)
    public final CommentResponse editReplyComment(@AuthUser User user, @RequestBody ReplyEditRequest commentRequest) {
        return repliesService.editReplyComment(user, commentRequest);
    }

    @Override
    @PutMapping(value = ReplyMappings.DELETE_REPLY)
    public final CommentResponse deleteReplyComment(@AuthUser User user, @RequestBody ReplyKeyResponse replyKey) {
        return repliesService.deleteReplyComment(user, replyKey);
    }

    @Override
    @PutMapping(value = ReplyMappings.UPDATE_LIKE_REPLY_COUNT)
    public final CommentResponse updateLikeCommentCount(@AuthUser User user, @RequestBody ReplyKeyResponse replyKey) {
        return repliesService.updateLikeCommentCount(user, replyKey);
    }

}
