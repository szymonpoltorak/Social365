package razepl.dev.social365.images.entities.image.comment;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.data.annotation.Version;
import razepl.dev.social365.images.api.constants.Params;

import java.util.Objects;

@Getter
@Setter
@Entity
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = @Index(columnList = Params.COMMENT_ID))
public class CommentImage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long imageId;

    @Column(nullable = false)
    private String username;

    private String commentId;

    private String postId;

    @Column(unique = true, nullable = false)
    private String imagePath;

    @Version
    private long version;

    @Override
    public final boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        Class<?> oEffectiveClass = o instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer()
                .getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy thisProxy ? thisProxy.getHibernateLazyInitializer()
                .getPersistentClass() : this.getClass();

        if (thisEffectiveClass != oEffectiveClass) {
            return false;
        }
        CommentImage image = (CommentImage) o;

        return Objects.equals(imageId, image.imageId) &&
                Objects.equals(username, image.username) &&
                Objects.equals(commentId, image.commentId) &&
                Objects.equals(imagePath, image.imagePath);
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer()
                .getPersistentClass().hashCode() : getClass().hashCode();
    }

}
