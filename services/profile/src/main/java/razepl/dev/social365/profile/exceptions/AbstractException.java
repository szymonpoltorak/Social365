package razepl.dev.social365.profile.exceptions;

import org.springframework.http.HttpStatusCode;

import java.io.Serial;

public abstract class AbstractException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 8192572744204878359L;

    private final HttpStatusCode status;

    AbstractException(HttpStatusCode status, String message) {
        super(message);

        this.status = status;
    }

    public final HttpStatusCode getStatusCode() {
        return status;
    }
}
