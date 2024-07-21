package razepl.dev.social365.images.entities.image.post.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import razepl.dev.social365.images.entities.image.post.PostImage;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostImagesRepository extends JpaRepository<PostImage, Long> {

    List<PostImage> findPostImagesByPostId(String postId);

    Page<PostImage> findPostImagesByUsername(String username, Pageable pageable);

    Optional<PostImage> findPostImageByImagePath(String imageUrl);

    void deleteAllByPostId(String postId);

}
