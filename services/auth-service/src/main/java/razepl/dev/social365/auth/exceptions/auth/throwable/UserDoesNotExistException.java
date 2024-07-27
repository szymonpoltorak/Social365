package razepl.dev.social365.auth.exceptions.auth.throwable;

import org.springframework.http.HttpStatus;

import java.io.Serial;

public class UserDoesNotExistException extends AbstractException {

    @Serial
    private static final long serialVersionUID = -4268637137006817600L;

    public UserDoesNotExistException(String username) {
        super(HttpStatus.NOT_FOUND, String.format("User with username: %s does not exist!", username));
    }

}
