package razepl.dev.social365.profile.utils.exceptions;

import org.springframework.http.HttpStatus;

import java.io.Serial;

public class AboutDetailsAlreadyExistException extends AbstractException {

    @Serial
    private static final long serialVersionUID = 5121009825338650062L;

    public AboutDetailsAlreadyExistException(String profileId) {
        super(HttpStatus.BAD_REQUEST, "About details already exist for profileId: " + profileId);
    }

}
