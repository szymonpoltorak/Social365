package razepl.dev.social365.profile.exceptions;

import org.springframework.http.HttpStatus;

import java.io.Serial;

public class ProfileNotFoundException extends AbstractException {

    @Serial
    private static final long serialVersionUID = -8699940704369771775L;

    public ProfileNotFoundException() {
        super(HttpStatus.NOT_FOUND, "Profile not found!");
    }

}
