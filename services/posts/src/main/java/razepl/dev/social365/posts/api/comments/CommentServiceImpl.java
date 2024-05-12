package razepl.dev.social365.posts.api.comments;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import razepl.dev.social365.posts.api.comments.data.CommentRequest;
import razepl.dev.social365.posts.api.comments.data.CommentResponse;
import razepl.dev.social365.posts.api.comments.interfaces.CommentService;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    @Override
    public Page<CommentResponse> getCommentsForPost(String postId, Pageable pageable) {
        return null;
    }

    @Override
    public CommentResponse addCommentToPost(CommentRequest commentRequest) {
        return null;
    }

    @Override
    public CommentResponse editComment(CommentRequest commentRequest) {
        return null;
    }

    @Override
    public CommentResponse deleteComment(String commentId) {
        return null;
    }

}
