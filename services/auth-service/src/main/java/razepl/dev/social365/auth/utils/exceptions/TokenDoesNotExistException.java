package razepl.dev.social365.auth.utils.exceptions;

import org.springframework.http.HttpStatus;

import java.io.Serial;

public class TokenDoesNotExistException extends AbstractException {

    @Serial
    private static final long serialVersionUID = -3677127531617118398L;

    public TokenDoesNotExistException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }

}
