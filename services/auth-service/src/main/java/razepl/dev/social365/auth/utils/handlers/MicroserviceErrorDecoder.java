package razepl.dev.social365.auth.utils.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import razepl.dev.social365.auth.api.auth.data.ExceptionResponse;
import razepl.dev.social365.auth.utils.exceptions.MicroserviceRequestException;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
public class MicroserviceErrorDecoder implements ErrorDecoder {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public final Exception decode(String methodKey, Response response) {
        ExceptionResponse exceptionResponse = getExceptionResponse(response.body());

        log.error("Error occurred while fetching data from microservice. Status : {}, Reason : {}", response.status(),
                exceptionResponse);

        return new MicroserviceRequestException(exceptionResponse);
    }

    private ExceptionResponse getExceptionResponse(Response.Body body) {
        try (InputStream inputStream = body.asInputStream()) {
            return objectMapper.readValue(inputStream, ExceptionResponse.class);

        } catch (IOException e) {
            log.error("Failed to decode error response", e);

            return ExceptionResponse
                    .builder()
                    .message("Server encountered error with fetching data")
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
                    .build();
        }
    }
}
