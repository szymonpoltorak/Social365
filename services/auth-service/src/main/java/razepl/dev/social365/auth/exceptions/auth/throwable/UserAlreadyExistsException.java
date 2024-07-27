package razepl.dev.social365.auth.exceptions.auth.throwable;

import org.springframework.http.HttpStatus;

import java.io.Serial;

public class UserAlreadyExistsException extends AbstractException {
    @Serial
    private static final long serialVersionUID = 8504365313100481550L;

    public UserAlreadyExistsException(String username) {
        super(HttpStatus.BAD_REQUEST, String.format("User with username %s already exists", username));
    }
}

