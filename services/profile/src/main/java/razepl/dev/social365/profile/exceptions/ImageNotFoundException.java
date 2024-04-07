package razepl.dev.social365.profile.exceptions;

import org.springframework.http.HttpStatus;

import java.io.Serial;

public class ImageNotFoundException extends AbstractException {

    @Serial
    private static final long serialVersionUID = -4901392696479678348L;

    public ImageNotFoundException() {
        super(HttpStatus.NOT_FOUND, "Image not found in the database.");
    }
}
