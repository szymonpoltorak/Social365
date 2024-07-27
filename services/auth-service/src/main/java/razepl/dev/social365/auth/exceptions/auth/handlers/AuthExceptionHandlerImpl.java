package razepl.dev.social365.auth.exceptions.auth.handlers;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import razepl.dev.social365.auth.config.api.auth.constants.AuthMessages;
import razepl.dev.social365.auth.config.api.auth.data.ConstraintExceptionResponse;
import razepl.dev.social365.auth.config.api.auth.data.ExceptionResponse;
import razepl.dev.social365.auth.exceptions.auth.throwable.AbstractException;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class AuthExceptionHandlerImpl {

    @ExceptionHandler(ConstraintViolationException.class)
    public final ResponseEntity<ConstraintExceptionResponse> handleConstraintValidationExceptions(ConstraintViolationException exception) {
        String className = exception.getClass().getSimpleName();
        List<String> errorResponse = exception.getConstraintViolations()
                .stream()
                .map(error -> String.format(AuthMessages.ERROR_FORMAT, error.getPropertyPath(), error.getMessage()))
                .toList();

        return buildResponseEntity(buildConstraintResponse(errorResponse, className), HttpStatus.BAD_REQUEST,
                errorResponse.toString(), className);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<ExceptionResponse> handleMethodArgValidExceptions(MethodArgumentNotValidException exception) {
        String className = exception.getClass().getSimpleName();
        String errorMessage = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> String.format(AuthMessages.ERROR_FORMAT, error.getField(), error.getDefaultMessage()))
                .collect(Collectors.joining(AuthMessages.ERROR_DELIMITER));

        return buildResponseEntity(buildResponse(errorMessage, className), HttpStatus.BAD_REQUEST, errorMessage, className);
    }

    @ExceptionHandler(AbstractException.class)
    public final ResponseEntity<ExceptionResponse> handleExceptions(AbstractException exception) {
        String errorMessage = exception.getMessage();
        String className = exception.getClass().getSimpleName();

        return buildResponseEntity(buildResponse(errorMessage, className), exception.getStatusCode(), errorMessage, className);
    }

    private <T> ResponseEntity<T> buildResponseEntity(T response, HttpStatusCode status, String errorMessage, String className) {
        log.error("Exception class name : {}\nError message : {}", className, errorMessage);

        return new ResponseEntity<>(response, status);
    }

    private ConstraintExceptionResponse buildConstraintResponse(List<String> errorResponse, String className) {
        return ConstraintExceptionResponse
                .builder()
                .errorResponse(errorResponse)
                .exceptionClassName(className)
                .build();
    }

    private ExceptionResponse buildResponse(String errorMessage, String exceptionClassName) {
        return ExceptionResponse.builder()
                .errorMessage(errorMessage)
                .exceptionClassName(exceptionClassName)
                .build();
    }

}
