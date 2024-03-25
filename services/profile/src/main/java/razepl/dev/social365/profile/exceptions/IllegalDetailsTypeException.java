package razepl.dev.social365.profile.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.io.Serial;

public class IllegalDetailsTypeException extends ResponseStatusException {

    @Serial
    private static final long serialVersionUID = 450427594506889243L;

    public IllegalDetailsTypeException() {
        super(HttpStatus.BAD_REQUEST, "Details type you provided is not valid for this operation.");
    }

}
