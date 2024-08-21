package razepl.dev.social365.profile.utils.exceptions;

import org.springframework.http.HttpStatus;

import java.io.Serial;

public class UsernameAlreadyClaimedException extends AbstractException {
    @Serial
    private static final long serialVersionUID = -9094267259789232957L;

    public UsernameAlreadyClaimedException(String username) {
        super(HttpStatus.BAD_REQUEST,
                "Username %s is already claimed. Please choose another one.".formatted(username));
    }
}
