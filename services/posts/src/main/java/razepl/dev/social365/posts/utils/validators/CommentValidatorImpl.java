package razepl.dev.social365.posts.utils.validators;

import org.springframework.stereotype.Component;
import razepl.dev.social365.posts.api.comments.data.CommentEditRequest;
import razepl.dev.social365.posts.utils.exceptions.InvalidCommentRequestException;
import razepl.dev.social365.posts.utils.validators.interfaces.CommentValidator;

@Component
public class CommentValidatorImpl implements CommentValidator {

    private static final int MAX_COMMENT_LENGTH = 500;
    private static final int MIN_COMMENT_LENGTH = 2;

    @Override
    public final void validateCommentRequest(CommentEditRequest commentEditRequest, String profileId) {
        String content = commentEditRequest.content();

        if (content == null || content.isEmpty() || content.isBlank()) {
            throw new InvalidCommentRequestException("Comment content cannot be empty!");
        }
        int contentLength = content.length();

        if (contentLength > MAX_COMMENT_LENGTH) {
            throw new InvalidCommentRequestException("Comment content cannot be longer than 500 characters!");
        }
        String objectId = commentEditRequest.commentKey().postId();

        if (objectId == null || objectId.isEmpty() || objectId.isBlank()) {
            throw new InvalidCommentRequestException("Comment object id cannot be null!");
        }
        if (profileId == null || profileId.isEmpty() || profileId.isBlank()) {
            throw new InvalidCommentRequestException("Comment profile id cannot be null!");
        }
        if (contentLength < MIN_COMMENT_LENGTH) {
            throw new InvalidCommentRequestException("Comment content cannot be shorter than 2 characters!");
        }
    }

}
