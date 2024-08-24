package razepl.dev.social365.auth.utils.exceptions;

import org.springframework.http.HttpStatus;

import java.io.Serial;

public class UserAccountIsLockedException extends AbstractException {

    @Serial
    private static final long serialVersionUID = -6282071273955062908L;

    public UserAccountIsLockedException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }

}
