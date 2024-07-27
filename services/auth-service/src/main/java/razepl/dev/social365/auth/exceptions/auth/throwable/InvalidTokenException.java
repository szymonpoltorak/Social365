package razepl.dev.social365.auth.exceptions.auth.throwable;

import org.springframework.http.HttpStatus;

import java.io.Serial;

public class InvalidTokenException extends AbstractException {
    @Serial
    private static final long serialVersionUID = 2384137801317432740L;

    public InvalidTokenException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
