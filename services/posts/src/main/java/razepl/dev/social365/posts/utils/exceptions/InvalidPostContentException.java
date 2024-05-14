package razepl.dev.social365.posts.utils.exceptions;

import org.springframework.http.HttpStatus;

import java.io.Serial;

public class InvalidPostContentException extends AbstractException {

    @Serial
    private static final long serialVersionUID = 6279881572544044823L;

    public InvalidPostContentException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
