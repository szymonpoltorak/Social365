package razepl.dev.social365.images.entities.image;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(indexes = @Index(columnList = Params.USERNAME))
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long imageId;

    private String username;

    private String profileId;

    @Column(unique = true, nullable = false)
    private String imagePath;

    @Enumerated(EnumType.STRING)
    private ImageType imageType;

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
        Image image = (Image) o;

        return Objects.equals(imageId, image.imageId) &&
                Objects.equals(username, image.username) &&
                Objects.equals(imagePath, image.imagePath);
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer()
                .getPersistentClass().hashCode() : getClass().hashCode();
    }
}
