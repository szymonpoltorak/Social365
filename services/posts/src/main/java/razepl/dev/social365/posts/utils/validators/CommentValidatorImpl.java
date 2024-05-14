package razepl.dev.social365.posts.utils.validators;

import org.springframework.stereotype.Component;
import razepl.dev.social365.posts.api.comments.data.CommentRequest;
import razepl.dev.social365.posts.utils.exceptions.InvalidCommentRequestException;
import razepl.dev.social365.posts.utils.validators.interfaces.CommentValidator;

@Component
public class CommentValidatorImpl implements CommentValidator {

    private static final int MAX_COMMENT_LENGTH = 500;
    private static final int MIN_COMMENT_LENGTH = 2;

    @Override
    public final void validateCommentRequest(CommentRequest commentRequest) {
        String content = commentRequest.content();

        if (content == null || content.isEmpty() || content.isBlank()) {
            throw new InvalidCommentRequestException("Comment content cannot be empty!");
        }
        int contentLength = content.length();

        if (contentLength > MAX_COMMENT_LENGTH) {
            throw new InvalidCommentRequestException("Comment content cannot be longer than 500 characters!");
        }
        if (commentRequest.objectId() == null) {
            throw new InvalidCommentRequestException("Comment object id cannot be null!");
        }
        if (commentRequest.profileId() == null) {
            throw new InvalidCommentRequestException("Comment profile id cannot be null!");
        }
        if (contentLength < MIN_COMMENT_LENGTH) {
            throw new InvalidCommentRequestException("Comment content cannot be shorter than 2 characters!");
        }
    }

}
