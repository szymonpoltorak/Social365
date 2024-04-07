package razepl.dev.social365.profile.exceptions;

import org.springframework.http.HttpStatus;

import java.io.Serial;

public class TooYoungForAccountException extends AbstractException {
    @Serial
    private static final long serialVersionUID = 3081415888675929730L;

    public TooYoungForAccountException() {
        super(HttpStatus.BAD_REQUEST, "User is too young for an account!");
    }
}
