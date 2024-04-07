package razepl.dev.social365.profile.clients.images;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import razepl.dev.social365.profile.exceptions.ImageNotFoundException;

class ImagesErrorDecoder implements ErrorDecoder {

    @Override
    public final Exception decode(String methodKey, Response response) {
        if (response.status() == HttpStatus.NOT_FOUND.value()) {
            return new ImageNotFoundException();
        }
        return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                "An error occurred while trying to fetch the image.");
    }

}
