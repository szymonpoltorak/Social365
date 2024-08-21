package razepl.dev.social365.profile.utils.exceptions;

import lombok.Getter;
import razepl.dev.social365.profile.utils.handler.ExceptionResponse;

import java.io.Serial;

@Getter
public class MicroserviceRequestException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -6824264128561641200L;

    private final ExceptionResponse exceptionResponse;

    public MicroserviceRequestException(ExceptionResponse exceptionResponse) {
        super(exceptionResponse.message());

        this.exceptionResponse = exceptionResponse;
    }

}
