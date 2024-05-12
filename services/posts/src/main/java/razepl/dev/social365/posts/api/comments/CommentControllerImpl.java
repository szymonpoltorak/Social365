package razepl.dev.social365.posts.api.comments;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import razepl.dev.social365.posts.api.comments.constants.CommentMappings;
import razepl.dev.social365.posts.api.comments.data.CommentRequest;
import razepl.dev.social365.posts.api.comments.data.CommentResponse;
import razepl.dev.social365.posts.api.comments.interfaces.CommentController;
import razepl.dev.social365.posts.api.comments.interfaces.CommentService;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = CommentMappings.COMMENT_MAPPING)
public class CommentControllerImpl implements CommentController {

    private final CommentService commentService;

    @Override
    @GetMapping(value = CommentMappings.GET_COMMENTS_FOR_POST)
    public final Page<CommentResponse> getCommentsForPost(String postId, Pageable pageable) {
        return commentService.getCommentsForPost(postId, pageable);
    }

    @Override
    @PostMapping(value = CommentMappings.ADD_COMMENT_TO_POST)
    public final CommentResponse addCommentToPost(CommentRequest commentRequest) {
        return commentService.addCommentToPost(commentRequest);
    }

    @Override
    @PutMapping(value = CommentMappings.EDIT_COMMENT)
    public final CommentResponse editComment(CommentRequest commentRequest) {
        return commentService.editComment(commentRequest);
    }

    @Override
    @DeleteMapping(value = CommentMappings.DELETE_COMMENT)
    public final CommentResponse deleteComment(String commentId) {
        return commentService.deleteComment(commentId);
    }
}
