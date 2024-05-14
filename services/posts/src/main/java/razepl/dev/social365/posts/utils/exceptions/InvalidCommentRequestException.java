package razepl.dev.social365.posts.utils.exceptions;

import org.springframework.http.HttpStatus;

import java.io.Serial;

public class InvalidCommentRequestException extends AbstractException {

    @Serial
    private static final long serialVersionUID = -1532241447098018105L;

    public InvalidCommentRequestException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }

}
