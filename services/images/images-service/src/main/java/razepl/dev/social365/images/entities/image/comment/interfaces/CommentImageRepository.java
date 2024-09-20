package razepl.dev.social365.images.entities.image.comment.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import razepl.dev.social365.images.entities.image.comment.CommentImage;

import java.util.Optional;

@Repository
public interface CommentImageRepository extends JpaRepository<CommentImage, Long> {

    Optional<CommentImage> findCommentImageByCommentId(String commentId);

    void deleteAllByPostId(String commentId);

}
