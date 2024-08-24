package razepl.dev.social365.profile.utils.exceptions;

import org.springframework.http.HttpStatus;

import java.io.Serial;

public class InvalidEmailException extends AbstractException {

    @Serial
    private static final long serialVersionUID = 7028644268316503815L;

    public InvalidEmailException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
