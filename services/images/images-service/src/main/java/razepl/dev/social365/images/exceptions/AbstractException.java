package razepl.dev.social365.images.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

import java.io.Serial;

public abstract class AbstractException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 8192572744204878359L;

    private final HttpStatus status;

    AbstractException(HttpStatus status, String reason) {
        super(reason);

        this.status = status;
    }

    public final HttpStatus getStatusCode() {
        return status;
    }
}
