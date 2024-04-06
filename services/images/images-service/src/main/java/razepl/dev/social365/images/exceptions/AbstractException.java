package razepl.dev.social365.images.exceptions;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

import java.io.Serial;

public abstract class AbstractException extends ResponseStatusException {

    @Serial
    private static final long serialVersionUID = 8192572744204878359L;

    AbstractException(HttpStatusCode status, String message) {
        super(status, message);
    }
}
