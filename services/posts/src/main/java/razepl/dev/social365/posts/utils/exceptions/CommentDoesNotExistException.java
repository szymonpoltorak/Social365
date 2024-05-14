package razepl.dev.social365.posts.utils.exceptions;

import org.springframework.http.HttpStatus;

import java.io.Serial;

public class CommentDoesNotExistException extends AbstractException {

    @Serial
    private static final long serialVersionUID = -151764624456879015L;

    public CommentDoesNotExistException(String commentId) {
        super(HttpStatus.NOT_FOUND, "Comment with id %s does not exist".formatted(commentId));
    }
}
