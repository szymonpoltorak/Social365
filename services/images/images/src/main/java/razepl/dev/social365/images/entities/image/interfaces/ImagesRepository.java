package razepl.dev.social365.images.entities.image.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import razepl.dev.social365.images.entities.image.Image;

@Repository
public interface ImagesRepository extends JpaRepository<Image, Long>{
}
