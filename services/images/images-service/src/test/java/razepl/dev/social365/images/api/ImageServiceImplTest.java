package razepl.dev.social365.images.api;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import razepl.dev.social365.images.api.data.ImageResponse;
import razepl.dev.social365.images.api.interfaces.FileManagementService;
import razepl.dev.social365.images.config.auth.User;
import razepl.dev.social365.images.entities.image.Image;
import razepl.dev.social365.images.entities.image.ImageType;
import razepl.dev.social365.images.entities.image.interfaces.ImagesMapper;
import razepl.dev.social365.images.entities.image.interfaces.ImagesRepository;
import razepl.dev.social365.images.exceptions.ImageNotFoundException;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {ImagesMapper.class, FileManagementService.class})
class ImageServiceImplTest {
    private static final String SHOULD_RETURN_IMAGE_RESPONSE_WITH_IMAGE_ID_USERNAME_AND_IMAGE_PATH = "Should return ImageResponse with imageId, username and imagePath";
    @InjectMocks
    private ImagesServiceImpl imagesService;

    @Mock
    private ImagesMapper imagesMapper;

    @Mock
    private FileManagementService fileManagementService;

    @Mock
    private ImagesRepository imagesRepository;

    @Test
    final void test_uploadImage_shouldReturnImageResponse() {
        // given
        MultipartFile image = new MockMultipartFile(
                "image",
                "image.jpg",
                "image/jpeg",
                "image".getBytes(StandardCharsets.UTF_8)
        );
        String username = "user@gmail.com";
        User user = User.builder().userId(1L).username(username).build();
        String filePath = String.format("tmp/%s/%s", username, image.getOriginalFilename());
        Image imageToSave = Image.builder().imageId(0L).imagePath(filePath).username(username).build();
        ImageResponse expected = new ImageResponse(0L, username, filePath);

        // when
        when(imagesRepository.save(imageToSave))
                .thenReturn(imageToSave);
        when(imagesMapper.toImageResponse(imageToSave))
                .thenReturn(expected);

        ImageResponse actual = imagesService.uploadImage(user, ImageType.PROFILE_IMAGE, image);

        // then
        assertEquals(expected, actual, SHOULD_RETURN_IMAGE_RESPONSE_WITH_IMAGE_ID_USERNAME_AND_IMAGE_PATH);
        verify(fileManagementService).saveFile(Path.of(filePath), image);
    }

    @Test
    final void test_getImagePath_Success() {
        long imageId = 1L;
        Image image = Image.builder().imageId(imageId).username("testUser").imagePath("testUser/image.jpg").build();
        ImageResponse imageResponse = new ImageResponse(image.getImageId(), image.getUsername(), image.getImagePath());

        when(imagesRepository.findImageByImageId(imageId)).thenReturn(Optional.of(image));
        when(imagesMapper.toImageResponse(image)).thenReturn(imageResponse);

        ImageResponse result = imagesService.getImagePath(imageId);

        assertEquals(imageResponse, result);
    }

    @Test
    final void test_getImagePath_ImageNotFound() {
        long imageId = 1L;

        when(imagesRepository.findImageByImageId(imageId)).thenReturn(Optional.empty());

        assertThrows(ImageNotFoundException.class, () -> imagesService.getImagePath(imageId));
    }

    @Test
    final void test_deleteImage_Success() {
        long imageId = 1L;
        Image image = Image.builder().imageId(imageId).username("testUser").imagePath("testUser/image.jpg").build();
        ImageResponse imageResponse = new ImageResponse(image.getImageId(), image.getUsername(), image.getImagePath());
        User user = User.builder().userId(imageId).build();

        when(imagesRepository.findImageByImageId(imageId)).thenReturn(Optional.of(image));
        when(imagesMapper.toImageResponse(image)).thenReturn(imageResponse);

        ImageResponse result = imagesService.deleteImage(imageId, user);

        verify(imagesRepository, times(1)).delete(image);
        verify(fileManagementService, times(1)).deleteFile(Path.of(image.getImagePath()));
        assertEquals(imageResponse, result, SHOULD_RETURN_IMAGE_RESPONSE_WITH_IMAGE_ID_USERNAME_AND_IMAGE_PATH);
    }

    @Test
    final void test_deleteImage_ImageNotFound() {
        long imageId = 1L;

        when(imagesRepository.findImageByImageId(imageId)).thenReturn(Optional.empty());

        assertThrows(ImageNotFoundException.class, () -> imagesService.deleteImage(imageId, User.builder().build()));
    }
}
