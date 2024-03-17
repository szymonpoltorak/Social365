package razepl.dev.social365.images.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import razepl.dev.social365.images.api.constants.ImagesMappings;
import razepl.dev.social365.images.api.constants.ImagesParams;
import razepl.dev.social365.images.api.data.ImageResponse;
import razepl.dev.social365.images.api.interfaces.ImagesController;
import razepl.dev.social365.images.api.interfaces.ImagesService;

@RequiredArgsConstructor
@RequestMapping(value = ImagesMappings.IMAGES_MAPPING)
public class ImagesControllerImpl implements ImagesController {
    private final ImagesService imagesService;

    @Override
    @PostMapping(value = ImagesMappings.UPLOAD_IMAGE_MAPPING)
    public final ImageResponse uploadImage(@RequestParam(ImagesParams.USERNAME) String username,
                                           @RequestBody MultipartFile image) {
        return imagesService.uploadImage(username, image);
    }

    @Override
    @GetMapping(value = ImagesMappings.GET_IMAGE_MAPPING)
    public final ImageResponse getImagePath(@RequestParam(ImagesParams.IMAGE_ID) long imageId) {
        return imagesService.getImagePath(imageId);
    }

    @Override
    @PutMapping(value = ImagesMappings.UPDATE_IMAGE_MAPPING)
    public final ImageResponse updateImage(@RequestParam(ImagesParams.IMAGE_ID) long imageId,
                                           @RequestBody MultipartFile image) {
        return imagesService.updateImage(imageId, image);
    }

    @Override
    @DeleteMapping(value = ImagesMappings.DELETE_IMAGE_MAPPING)
    public final ImageResponse deleteImage(@RequestParam(ImagesParams.IMAGE_ID) long imageId) {
        return imagesService.deleteImage(imageId);
    }
}
