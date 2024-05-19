package razepl.dev.social365.profile.clients.images;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import razepl.dev.social365.profile.exceptions.ImageNotFoundException;

@Slf4j
class ImagesErrorDecoder implements ErrorDecoder {

    @Override
    public final Exception decode(String methodKey, Response response) {
        if (response.status() == HttpStatus.NOT_FOUND.value()) {
            return new ImageNotFoundException();
        }
        log.error("Error occurred while trying to fetch the image. Status code: {}, Reason : {}", response.status(),
                response.body());

        return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                "An error occurred while trying to fetch the image.");
    }

}
