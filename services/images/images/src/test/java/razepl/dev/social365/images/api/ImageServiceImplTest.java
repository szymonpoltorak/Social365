package razepl.dev.social365.images.api;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import razepl.dev.social365.images.api.data.ImageResponse;
import razepl.dev.social365.images.api.interfaces.FileManagementService;
import razepl.dev.social365.images.entities.image.Image;
import razepl.dev.social365.images.entities.image.interfaces.ImagesMapper;
import razepl.dev.social365.images.entities.image.interfaces.ImagesRepository;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {ImagesMapper.class, FileManagementService.class})
class ImageServiceImplTest {
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
        String filePath = String.format("%s/%s", username, image.getOriginalFilename());
        Image imageToSave = Image.builder().imageId(0L).imagePath(filePath).username(username).build();
        ImageResponse expected = new ImageResponse(0L, username, filePath);

        // when
        when(imagesRepository.save(imageToSave))
                .thenReturn(imageToSave);
        when(imagesMapper.toImageResponse(imageToSave))
                .thenReturn(expected);

        ImageResponse actual = imagesService.uploadImage(username, image);

        // then
        assertEquals(expected, actual, "Should return ImageResponse with imageId, username and imagePath");
        verify(fileManagementService).saveFile(filePath, image);
    }
}
