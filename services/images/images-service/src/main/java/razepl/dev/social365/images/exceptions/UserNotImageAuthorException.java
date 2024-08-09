package razepl.dev.social365.images.exceptions;

import org.springframework.http.HttpStatus;

import java.io.Serial;

public class UserNotImageAuthorException extends AbstractException {

    @Serial
    private static final long serialVersionUID = -6531315840338065430L;

    public UserNotImageAuthorException(String username) {
        super(HttpStatus.UNAUTHORIZED, "User is not the author of the image: %s".formatted(username));
    }

}
