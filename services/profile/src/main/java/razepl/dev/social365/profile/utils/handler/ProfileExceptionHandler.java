package razepl.dev.social365.profile.utils.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import razepl.dev.social365.profile.utils.exceptions.AbstractException;
import razepl.dev.social365.profile.utils.exceptions.MicroserviceRequestException;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class ProfileExceptionHandler {

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
                .timeStamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(response, exception.getStatusCode());
    }

    @ExceptionHandler(MicroserviceRequestException.class)
    public final ResponseEntity<ExceptionResponse> handleMicroserviceRequestException(MicroserviceRequestException exception) {
        ExceptionResponse response = exception.getExceptionResponse();

        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.statusCode()));
    }

}
