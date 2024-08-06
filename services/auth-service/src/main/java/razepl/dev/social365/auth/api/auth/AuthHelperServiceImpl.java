package razepl.dev.social365.auth.api.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import razepl.dev.social365.auth.api.auth.data.AuthResponse;
import razepl.dev.social365.auth.api.auth.data.LoginRequest;
import razepl.dev.social365.auth.api.auth.data.RegisterRequest;
import razepl.dev.social365.auth.api.auth.data.ResetPasswordRequest;
import razepl.dev.social365.auth.api.auth.data.TokenResponse;
import razepl.dev.social365.auth.api.auth.interfaces.AuthHelperService;
import razepl.dev.social365.auth.clients.profile.data.Profile;
import razepl.dev.social365.auth.config.constants.TokenRevokeStatus;
import razepl.dev.social365.auth.config.jwt.constants.JwtClaims;
import razepl.dev.social365.auth.config.jwt.interfaces.JwtService;
import razepl.dev.social365.auth.entities.attempts.LoginAttempt;
import razepl.dev.social365.auth.entities.attempts.interfaces.LoginAttemptRepository;
import razepl.dev.social365.auth.entities.user.User;
import razepl.dev.social365.auth.entities.user.interfaces.UserRepository;
import razepl.dev.social365.auth.exceptions.auth.throwable.InvalidTokenException;
import razepl.dev.social365.auth.exceptions.auth.throwable.UserDoesNotExistException;


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
    public final TokenResponse buildTokensIntoResponse(String authToken, String refreshToken) {
        return buildResponse(authToken, refreshToken);
    }

    @Override
    public final AuthResponse buildTokensIntoResponse(User user, Profile profile, TokenRevokeStatus shouldBeRevoked) {
        Jwt authToken = jwtService.generateToken(user);
        Jwt refreshToken = jwtService.generateRefreshToken(user);

        if (shouldBeRevoked == TokenRevokeStatus.TO_REVOKE) {
            jwtService.revokeUserTokens(user);
        }
        return AuthResponse
                .builder()
                .token(buildResponse(authToken, refreshToken))
                .profile(profile)
                .build();
    }

    @Override
    public final User buildRequestIntoUser(RegisterRequest registerRequest, LoginAttempt loginAttempt) {
        return User
                .builder()
                .firstName(registerRequest.firstName())
                .username(registerRequest.username())
                .lastName(registerRequest.lastName())
                .password(passwordEncoder.encode(registerRequest.password()))
                .jwtVersion(0L)
                .loginAttempt(loginAttempt)
                .build();
    }

    @Override
    public final void executeUserAuthenticationProcess(LoginAttempt loginAttempt, LoginRequest loginRequest) {
        try {
            loginAttempt.incrementAttempts();

            log.info("Authenticating user : {}", loginRequest.username());

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginRequest.username(), loginRequest.password())
            );

            loginAttempt.resetAttempts();

        } catch (AuthenticationException e) {
            log.error("User login failed! User : {}", loginRequest.username());

            throw new UserDoesNotExistException(loginRequest.username());
        } finally {
            loginAttemptRepository.save(loginAttempt);
        }
    }

    @Override
    public final void executePasswordResetProcess(ResetPasswordRequest request, User user) {
        long resetPasswordVersion = jwtService.getJwtVersionClaimFromToken(request.resetPasswordToken())
                .orElseThrow(() -> new InvalidTokenException("Token does not contain jwt version!"));
        Jwt jwt = jwtService.decode(request.resetPasswordToken());

        if (jwt.getClaim(JwtClaims.PASSWORD_RESET) == null) {
            throw new InvalidTokenException("Password reset Token does not contain password reset claim!");
        }
        if (resetPasswordVersion != user.getJwtVersion()) {
            throw new InvalidTokenException("Reset password jwt version is not equal to user's jwt version!");
        }
        String encodedPassword = passwordEncoder.encode(request.newPassword());

        log.info("Setting new password : {}", encodedPassword);

        user.setPassword(encodedPassword);
        user.incrementJwtVersion();

        userRepository.save(user);

    }

    private TokenResponse buildResponse(Jwt authToken, Jwt refreshToken) {
        return buildResponse(authToken.getTokenValue(), refreshToken.getTokenValue());
    }

    private TokenResponse buildResponse(String authToken, String refreshToken) {
        return TokenResponse
                .builder()
                .authToken(authToken)
                .refreshToken(refreshToken)
                .build();
    }

}
