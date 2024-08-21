package razepl.dev.social365.auth.utils.exceptions;

import org.springframework.http.HttpStatus;

import java.io.Serial;

public class TokensUserNotFoundException extends AbstractException {
    @Serial
    private static final long serialVersionUID = -9131358509078213067L;

    public TokensUserNotFoundException(String username) {
        super(HttpStatus.NOT_FOUND, String.format("User with username %s not found", username));
    }
}
