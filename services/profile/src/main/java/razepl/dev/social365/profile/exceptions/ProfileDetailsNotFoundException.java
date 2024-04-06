package razepl.dev.social365.profile.exceptions;

import org.springframework.http.HttpStatus;

import java.io.Serial;

public class ProfileDetailsNotFoundException extends AbstractException {

    @Serial
    private static final long serialVersionUID = -3462745688555095788L;

    public ProfileDetailsNotFoundException() {
        super(HttpStatus.NOT_FOUND, "Profile details not found");
    }
}
