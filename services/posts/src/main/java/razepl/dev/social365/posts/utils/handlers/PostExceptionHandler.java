package razepl.dev.social365.posts.utils.handlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import razepl.dev.social365.posts.utils.exceptions.AbstractException;
import razepl.dev.social365.posts.utils.exceptions.MicroserviceRequestException;
import razepl.dev.social365.posts.utils.handlers.data.ExceptionResponse;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@RestControllerAdvice
public class PostExceptionHandler {

    @ExceptionHandler(AbstractException.class)
    public final ResponseEntity<ExceptionResponse> handleException(AbstractException exception) {
        log.error("An exception thrown: {}", exception.getClass().getSimpleName());
        log.error("An exception message: {}", exception.getLocalizedMessage());
        log.error("An exception status code: {}", exception.getStatusCode());

        ExceptionResponse response = ExceptionResponse
                .builder()
                .message(exception.getMessage())
                .statusCode(exception.getStatusCode().value())
                .status(exception.getStatusCode().toString())
                .timeStamp(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME))
                .build();

        return new ResponseEntity<>(response, exception.getStatusCode());
    }

    @ExceptionHandler(MicroserviceRequestException.class)
    public final ResponseEntity<ExceptionResponse> handleMicroserviceRequestException(MicroserviceRequestException exception) {
        ExceptionResponse response = exception.getExceptionResponse();

        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.statusCode()));
    }

}
