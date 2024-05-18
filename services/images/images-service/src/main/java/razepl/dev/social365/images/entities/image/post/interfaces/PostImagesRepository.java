package razepl.dev.social365.images.entities.image.post.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import razepl.dev.social365.images.entities.image.post.PostImage;

@Repository
public interface PostImagesRepository extends JpaRepository<PostImage, Long> {
}
