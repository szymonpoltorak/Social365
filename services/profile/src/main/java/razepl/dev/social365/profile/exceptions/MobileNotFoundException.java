package razepl.dev.social365.profile.exceptions;

import org.springframework.http.HttpStatus;

import java.io.Serial;

public class MobileNotFoundException extends AbstractException {

    @Serial
    private static final long serialVersionUID = -8305950178771713350L;

    public MobileNotFoundException(String profileId) {
        super(HttpStatus.NOT_FOUND, "Mobile not found for profileId: " + profileId);
    }

}
