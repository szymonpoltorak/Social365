package razepl.dev.social365.auth.exceptions.auth.throwable;

import jakarta.validation.ValidationException;

import java.io.Serial;

public class PasswordValidationException extends ValidationException {

    @Serial
    private static final long serialVersionUID = 5819893719916237905L;

    public PasswordValidationException(String message) {
        super(message);
    }

}
