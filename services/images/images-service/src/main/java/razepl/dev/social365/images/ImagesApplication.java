package razepl.dev.social365.images;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import razepl.dev.social365.images.entities.image.Image;
import razepl.dev.social365.images.entities.image.ImageType;
import razepl.dev.social365.images.entities.image.interfaces.ImagesRepository;

import java.nio.file.Path;

@SpringBootApplication
public class ImagesApplication {

    private static final String IMAGE_VOLUME_PATH = System.getenv("IMAGE_VOLUME_PATH");

    public static void main(String[] args) {
        SpringApplication.run(ImagesApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(ImagesRepository imagesRepository) {
        return args -> {
            String username = "nouser@example.com";

            if (!imagesRepository.existsByUsername(username)) {
                String path = Path.of(IMAGE_VOLUME_PATH, username, "shiba1.jpg").toString();

                imagesRepository.save(Image.builder().username(username).imagePath(path).imageType(ImageType.PROFILE_IMAGE).build());
            }
        };
    }

}
