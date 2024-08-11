package razepl.dev.social365.auth.exceptions.auth.throwable;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.io.Serial;

public abstract class AbstractException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 3046130207980793629L;

    private final HttpStatus status;

    AbstractException(HttpStatus status, String reason) {
        super(reason);

        this.status = status;
    }

    public final HttpStatus getStatusCode() {
        return status;
    }

}
