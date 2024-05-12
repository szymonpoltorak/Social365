package razepl.dev.social365.posts.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.io.Serial;

public abstract class AbstractException extends ResponseStatusException {

    @Serial
    private static final long serialVersionUID = 3046130207980793629L;

    public AbstractException(HttpStatus status, String reason) {
        super(status, reason);
    }

}
