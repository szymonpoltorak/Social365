package razepl.dev.social365.auth.entities.attempts.interfaces;

public interface AttemptController {

    void incrementAttempts();

    void resetAttempts();

    boolean isAccountNonLocked();

}
