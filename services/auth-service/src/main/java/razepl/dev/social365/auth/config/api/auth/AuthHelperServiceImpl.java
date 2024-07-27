package razepl.dev.social365.auth.config.api.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import razepl.dev.social365.auth.config.api.auth.data.LoginRequest;
import razepl.dev.social365.auth.config.api.auth.data.RegisterRequest;
import razepl.dev.social365.auth.config.api.auth.data.ResetPasswordRequest;
import razepl.dev.social365.auth.config.api.auth.interfaces.AuthHelperService;
import razepl.dev.social365.auth.config.jwt.interfaces.JwtService;
import razepl.dev.social365.auth.entities.attempts.LoginAttempt;
import razepl.dev.social365.auth.entities.attempts.interfaces.LoginAttemptRepository;
import razepl.dev.social365.auth.entities.user.User;
import razepl.dev.social365.auth.entities.user.interfaces.UserRepository;
import razepl.dev.social365.auth.exceptions.auth.throwable.InvalidTokenException;


@Slf4j
@Service
@RequiredArgsConstructor
public class AuthHelperServiceImpl implements AuthHelperService {

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final LoginAttemptRepository loginAttemptRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Override
    public final User buildRequestIntoUser(RegisterRequest registerRequest, LoginAttempt loginAttempt) {
        return User
                .builder()
                .name(registerRequest.name())
                .username(registerRequest.username())
                .surname(registerRequest.surname())
                .password(passwordEncoder.encode(registerRequest.password()))
                .jwtVersion(0L)
                .loginAttempt(loginAttempt)
                .build();
    }

    @Override
    public final void executeUserAuthenticationProcess(LoginAttempt loginAttempt, LoginRequest loginRequest) {
        try {
            loginAttempt.incrementAttempts();

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginRequest.username(), loginRequest.password())
            );
            loginAttempt.resetAttempts();

        } catch (AuthenticationException e) {
            log.error("User login failed! User : {}", loginRequest.username());

            throw new UsernameNotFoundException("User login failed!", e);
        } finally {
            loginAttemptRepository.save(loginAttempt);
        }
    }

    @Override
    public final void executePasswordResetProcess(ResetPasswordRequest request, User user) {
        long resetPasswordVersion = jwtService.getJwtVersionClaimFromToken(request.resetPasswordToken())
                .orElseThrow(() -> new InvalidTokenException("Token does not contain jwt version!"));

        if (resetPasswordVersion != user.getJwtVersion()) {
            throw new InvalidTokenException("Reset password jwt version is not equal to user's jwt version!");
        }
        String encodedPassword = passwordEncoder.encode(request.newPassword());

        log.info("Setting new password : {}", encodedPassword);

        user.setPassword(encodedPassword);
        user.incrementJwtVersion();

        userRepository.save(user);

    }

}
