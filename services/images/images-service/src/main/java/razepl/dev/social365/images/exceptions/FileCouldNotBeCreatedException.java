package razepl.dev.social365.images.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.io.Serial;

public class FileCouldNotBeCreatedException extends ResponseStatusException {
    @Serial
    private static final long serialVersionUID = -3739234899396130772L;

    public FileCouldNotBeCreatedException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
