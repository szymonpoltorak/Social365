package razepl.dev.social365.images.entities.image.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import razepl.dev.social365.images.entities.image.Image;
import razepl.dev.social365.images.entities.image.ImageType;

import java.util.Optional;

@Repository
public interface ImagesRepository extends JpaRepository<Image, Long>{

    Optional<Image> findImageByImageId(long imageId);

    boolean existsByUsername(String username);

    Optional<Image> findImageByImagePath(String imageUrl);

    Optional<Image> findImageByProfileIdAndImageType(String profileId, ImageType imageType);

}
