package razepl.dev.social365.auth.entities.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.security.core.GrantedAuthority;
import razepl.dev.social365.auth.entities.attempts.LoginAttempt;
import razepl.dev.social365.auth.entities.user.interfaces.ServiceUser;

import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serial;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

import static razepl.dev.social365.auth.entities.user.constants.Constants.USERS_TABLE_NAME;
import static razepl.dev.social365.auth.entities.user.constants.Constants.USER_PACKAGE;
import static razepl.dev.social365.auth.entities.user.constants.UserValidation.EMAIL_MAX_LENGTH;
import static razepl.dev.social365.auth.entities.user.constants.UserValidation.EMAIL_MIN_LENGTH;
import static razepl.dev.social365.auth.entities.user.constants.UserValidation.NAME_MAX_LENGTH;
import static razepl.dev.social365.auth.entities.user.constants.UserValidation.NAME_MIN_LENGTH;
import static razepl.dev.social365.auth.entities.user.constants.UserValidation.NAME_PATTERN;
import static razepl.dev.social365.auth.entities.user.constants.UserValidationMessages.EMAIL_MESSAGE;
import static razepl.dev.social365.auth.entities.user.constants.UserValidationMessages.EMAIL_NULL_MESSAGE;
import static razepl.dev.social365.auth.entities.user.constants.UserValidationMessages.FIRST_NAME_NULL_MESSAGE;
import static razepl.dev.social365.auth.entities.user.constants.UserValidationMessages.FIRST_NAME_PATTERN_MESSAGE;
import static razepl.dev.social365.auth.entities.user.constants.UserValidationMessages.FIRST_NAME_SIZE_MESSAGE;
import static razepl.dev.social365.auth.entities.user.constants.UserValidationMessages.LAST_NAME_NULL_MESSAGE;
import static razepl.dev.social365.auth.entities.user.constants.UserValidationMessages.LAST_NAME_PATTERN_MESSAGE;
import static razepl.dev.social365.auth.entities.user.constants.UserValidationMessages.LAST_NAME_SIZE_MESSAGE;
import static razepl.dev.social365.auth.entities.user.constants.UserValidationMessages.PASSWORD_NULL_MESSAGE;


@Slf4j
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = USERS_TABLE_NAME)
public class User implements ServiceUser {

    @Serial
    private static final long serialVersionUID = 884980275324187578L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userId;

    @Column(unique = true)
    private String profileId;

    @NotNull(message = FIRST_NAME_NULL_MESSAGE)
    @Size(min = NAME_MIN_LENGTH, max = NAME_MAX_LENGTH, message = FIRST_NAME_SIZE_MESSAGE)
    @Pattern(regexp = NAME_PATTERN, message = FIRST_NAME_PATTERN_MESSAGE)
    private String firstName;

    @NotNull(message = LAST_NAME_NULL_MESSAGE)
    @Size(min = NAME_MIN_LENGTH, max = NAME_MAX_LENGTH, message = LAST_NAME_SIZE_MESSAGE)
    @Pattern(regexp = NAME_PATTERN, message = LAST_NAME_PATTERN_MESSAGE)
    private String lastName;

    @NotNull(message = EMAIL_NULL_MESSAGE)
    @Column(unique = true)
    @Size(min = EMAIL_MIN_LENGTH, max = EMAIL_MAX_LENGTH)
    @Email(message = EMAIL_MESSAGE)
    private String username;

    @JsonIgnore
    @NotNull(message = PASSWORD_NULL_MESSAGE)
    private String password;

    private long jwtVersion;

    @OneToOne
    private LoginAttempt loginAttempt;

    @Override
    public final String getFullName() {
        return String.format("%s %s", firstName, lastName);
    }

    @Override
    public final Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public final void incrementJwtVersion() {
        jwtVersion++;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public final boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public final boolean isAccountNonLocked() {
        return loginAttempt.isAccountNonLocked();
    }

    @Override
    public final boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public final boolean isEnabled() {
        return true;
    }

    @Serial
    private void readObject(ObjectInputStream in) throws ClassNotFoundException, NotSerializableException {
        throw new NotSerializableException(USER_PACKAGE);
    }

    @Serial
    private void writeObject(ObjectOutputStream out) throws NotSerializableException {
        throw new NotSerializableException(USER_PACKAGE);
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        Class<?> oEffectiveClass = o instanceof HibernateProxy hibernateProxy ? hibernateProxy
                .getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy thisHibernateProxy ? thisHibernateProxy
                .getHibernateLazyInitializer().getPersistentClass() : this.getClass();

        if (thisEffectiveClass != oEffectiveClass) {
            return false;
        }
        User user = (User) o;

        return Objects.equals(userId, user.userId);
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy hibernateProxy ? hibernateProxy.getHibernateLazyInitializer()
                .getPersistentClass().hashCode() : getClass().hashCode();
    }

}
