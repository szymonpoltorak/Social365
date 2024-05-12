package razepl.dev.social365.posts.utils.handlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import razepl.dev.social365.posts.utils.exceptions.AbstractException;
import razepl.dev.social365.posts.utils.handlers.data.ExceptionResponse;

@Slf4j
@RestControllerAdvice
public class PostExceptionHandler {

    @ExceptionHandler(AbstractException.class)
    public final ResponseEntity<ExceptionResponse> handleException(AbstractException ex) {
        ExceptionResponse response = ExceptionResponse
                .builder()
                .className(ex.getClass().getSimpleName())
                .code(ex.getStatusCode().toString())
                .message(ex.getReason())
                .build();

        log.error("Exception: {}", response);
        log.error("", ex);

        return ResponseEntity.status(ex.getStatusCode()).body(response);
    }

}
