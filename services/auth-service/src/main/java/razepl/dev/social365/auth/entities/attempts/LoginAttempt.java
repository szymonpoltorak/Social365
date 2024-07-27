package razepl.dev.social365.auth.entities.attempts;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import razepl.dev.social365.auth.entities.attempts.interfaces.AttemptController;

import java.time.LocalTime;

@Getter
@ToString
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginAttempt implements AttemptController {
    private static final long LOCK_ATTEMPTS_MULTIPLIER = 5L;
    private static final long MINIMUM_LOCK_TIME = 3L;
    private static final long NO_ATTEMPTS = 0L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long loginAttemptId;

    private long attempts;

    private LocalTime dateOfLock;

    @Override
    public final void incrementAttempts() {
        attempts++;

        if (attempts % LOCK_ATTEMPTS_MULTIPLIER != NO_ATTEMPTS) {
            return;
        }
        long multiplier = attempts / LOCK_ATTEMPTS_MULTIPLIER;

        dateOfLock = LocalTime.now().plusSeconds(multiplier * MINIMUM_LOCK_TIME);
    }

    @Override
    public final void resetAttempts() {
        attempts = NO_ATTEMPTS;
        dateOfLock = LocalTime.MIN;
    }

    @Override
    public final boolean isAccountNonLocked() {
        return dateOfLock.isBefore(LocalTime.now());
    }
}
