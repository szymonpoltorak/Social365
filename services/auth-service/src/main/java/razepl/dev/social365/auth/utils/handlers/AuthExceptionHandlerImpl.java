package razepl.dev.social365.auth.utils.handlers;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import razepl.dev.social365.auth.api.auth.constants.AuthMessages;
import razepl.dev.social365.auth.api.auth.data.ConstraintExceptionResponse;
import razepl.dev.social365.auth.api.auth.data.ExceptionResponse;
import razepl.dev.social365.auth.utils.exceptions.AbstractException;
import razepl.dev.social365.auth.utils.exceptions.MicroserviceRequestException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class AuthExceptionHandlerImpl {

    @ExceptionHandler(ConstraintViolationException.class)
    public final ResponseEntity<ConstraintExceptionResponse> handleConstraintValidationExceptions(ConstraintViolationException exception) {
        List<String> errorResponse = exception
                .getConstraintViolations()
                .stream()
                .map(error -> String.format(AuthMessages.ERROR_FORMAT, error.getPropertyPath(), error.getMessage()))
                .toList();

        ConstraintExceptionResponse response = buildConstraintResponse(errorResponse);

        log.error("An exception response for constraint validation : {}", response);

        return new ResponseEntity<>(buildConstraintResponse(errorResponse), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<ExceptionResponse> handleMethodArgValidExceptions(MethodArgumentNotValidException exception) {
        String errorMessage = exception
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> String.format(AuthMessages.ERROR_FORMAT, error.getField(), error.getDefaultMessage()))
                .collect(Collectors.joining(AuthMessages.ERROR_DELIMITER));
        ExceptionResponse response = buildResponse(errorMessage, HttpStatus.BAD_REQUEST);

        log.error("Exception response for method argument validation : {}", response);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AbstractException.class)
    public final ResponseEntity<ExceptionResponse> handleExceptions(AbstractException exception) {
        String errorMessage = exception.getMessage();
        ExceptionResponse response = buildResponse(errorMessage, exception.getStatusCode());

        log.error("An exception response : {}", response);

        return new ResponseEntity<>(response, exception.getStatusCode());
    }

    @ExceptionHandler(MicroserviceRequestException.class)
    public final ResponseEntity<ExceptionResponse> handleMicroserviceRequestException(MicroserviceRequestException exception) {
        ExceptionResponse response = exception.getExceptionResponse();

        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.statusCode()));
    }

    private ConstraintExceptionResponse buildConstraintResponse(List<String> errorResponse) {
        return ConstraintExceptionResponse
                .builder()
                .message(errorResponse)
                .timeStamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.name())
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .build();
    }

    private ExceptionResponse buildResponse(String errorMessage, HttpStatus status) {
        return ExceptionResponse.builder()
                .message(errorMessage)
                .timeStamp(LocalDateTime.now())
                .status(status.name())
                .statusCode(status.value())
                .build();
    }

}
