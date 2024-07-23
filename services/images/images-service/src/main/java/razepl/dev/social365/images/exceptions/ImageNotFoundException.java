package razepl.dev.social365.images.exceptions;

import org.springframework.http.HttpStatus;

import java.io.Serial;

public class ImageNotFoundException extends AbstractException {
    @Serial
    private static final long serialVersionUID = 530169841209136106L;

    public ImageNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
