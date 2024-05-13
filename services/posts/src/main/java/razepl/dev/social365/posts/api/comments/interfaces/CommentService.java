package razepl.dev.social365.posts.api.comments.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import razepl.dev.social365.posts.api.comments.data.CommentRequest;
import razepl.dev.social365.posts.api.comments.data.CommentResponse;

public interface CommentService {

    Page<CommentResponse> getCommentsForPost(String postId, String profileId, Pageable pageable);

    CommentResponse addCommentToPost(CommentRequest commentRequest);

    CommentResponse editComment(CommentRequest commentRequest);

    CommentResponse deleteComment(String commentId, String profileId);

}
