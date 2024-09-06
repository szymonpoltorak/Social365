package razepl.dev.social365.notifications.clients.images;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import razepl.dev.social365.notifications.api.notifications.constants.Params;
import razepl.dev.social365.notifications.clients.images.constants.ImagesMappings;
import razepl.dev.social365.notifications.clients.images.data.ImageResponse;

@FunctionalInterface
@FeignClient(name = "IMAGES-SERVICE", url = "http://images-service:8081")
public interface ImageService {

    @GetMapping(value = ImagesMappings.GET_PROFILE_IMAGE_BY_PROFILE_ID_MAPPING)
    ImageResponse getProfileImageByProfileId(@RequestParam(Params.PROFILE_ID) String profileId);

}
