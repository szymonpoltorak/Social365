package razepl.dev.social365.profile.clients.images;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import razepl.dev.social365.profile.clients.images.constants.ImagesMappings;
import razepl.dev.social365.profile.clients.images.constants.ImagesParams;
import razepl.dev.social365.profile.clients.images.data.ImageResponse;

@FunctionalInterface
@FeignClient(name = "IMAGES-SERVICE")
public interface ImagesServiceClient {

    @GetMapping(value = ImagesMappings.GET_IMAGE_MAPPING)
    ImageResponse getImagePath(@RequestParam(ImagesParams.IMAGE_ID) long imageId);

}
