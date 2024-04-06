package razepl.dev.social365.images.exceptions.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import razepl.dev.social365.images.exceptions.AbstractException;

@Slf4j
@RestControllerAdvice
public class ImagesExceptionHandler {

    @ExceptionHandler(AbstractException.class)
    public final ExceptionResponse handleException(AbstractException exception) {
        log.error("An exception thrown: {}", exception.getClass().getSimpleName());
        log.error("An exception message: {}", exception.getLocalizedMessage());
        log.error("An exception status code: {}", exception.getStatusCode());

        return ExceptionResponse
                .builder()
                .message(exception.getMessage())
                .statusCode(exception.getStatusCode().value())
                .status(exception.getStatusCode().toString())
                .build();
    }

}
