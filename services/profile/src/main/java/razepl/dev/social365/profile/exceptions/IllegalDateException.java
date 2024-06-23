package razepl.dev.social365.profile.exceptions;

import org.springframework.http.HttpStatus;

import java.io.Serial;

public class IllegalDateException extends AbstractException {

    @Serial
    private static final long serialVersionUID = 5488509105990490600L;

    public IllegalDateException() {
        super(HttpStatus.BAD_REQUEST, "Given date is invalid.");
    }

}
