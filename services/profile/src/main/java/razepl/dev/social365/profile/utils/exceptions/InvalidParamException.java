package razepl.dev.social365.profile.utils.exceptions;

import org.springframework.http.HttpStatus;

import java.io.Serial;

public class InvalidParamException extends AbstractException {

    @Serial
    private static final long serialVersionUID = 1275956176402666323L;

    public InvalidParamException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
