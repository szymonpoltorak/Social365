package razepl.dev.social365.auth.api.auth;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import razepl.dev.social365.auth.api.auth.data.AuthResponse;
import razepl.dev.social365.auth.api.auth.data.LoginRequest;
import razepl.dev.social365.auth.api.auth.data.RegisterRequest;
import razepl.dev.social365.auth.api.auth.data.ResetPasswordRequest;
import razepl.dev.social365.auth.api.auth.data.SimpleStringResponse;
import razepl.dev.social365.auth.api.auth.interfaces.AuthHelperService;
import razepl.dev.social365.auth.api.auth.interfaces.AuthService;
import razepl.dev.social365.auth.api.auth.interfaces.LoginDeviceHandler;
import razepl.dev.social365.auth.config.constants.TokenRevokeStatus;
import razepl.dev.social365.auth.config.jwt.interfaces.JwtService;
import razepl.dev.social365.auth.entities.attempts.LoginAttempt;
import razepl.dev.social365.auth.entities.attempts.interfaces.LoginAttemptRepository;
import razepl.dev.social365.auth.entities.user.User;
import razepl.dev.social365.auth.entities.user.interfaces.UserRepository;
import razepl.dev.social365.auth.exceptions.auth.throwable.InvalidTokenException;
import razepl.dev.social365.auth.exceptions.auth.throwable.TokenDoesNotExistException;
import razepl.dev.social365.auth.exceptions.auth.throwable.UserAlreadyExistsException;

import java.time.LocalTime;
import java.util.Collections;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private static final String USER_NOT_EXIST_MESSAGE = "Such user does not exist!";
    private static final long PASSWORD_REFRESH_TOKEN_TIME = 600_000L;

    private final UserRepository userRepository;
    private final LoginAttemptRepository loginAttemptRepository;
    private final LoginDeviceHandler loginDeviceFilter;
    private final JwtService jwtService;
    private final AuthHelperService authHelperService;

    @Override
    public final AuthResponse register(RegisterRequest registerRequest, HttpServletRequest request) {
        log.info("Registering user with data: \n{}", registerRequest);

        validateUserRegisterData(registerRequest);

        LoginAttempt loginAttempt = LoginAttempt
                .builder()
                .attempts(0L)
                .dateOfLock(LocalTime.MIN)
                .build();
        LoginAttempt newLoginAttempt = loginAttemptRepository.save(loginAttempt);

        User user = authHelperService.buildRequestIntoUser(registerRequest, newLoginAttempt);

        User newUser = userRepository.save(user);

        loginDeviceFilter.addNewDeviceToUserLoggedInDevices(newUser, request);

        log.info("Building token response for user : {}", newUser);

        return jwtService.buildTokensIntoResponse(newUser, TokenRevokeStatus.NOT_TO_REVOKE);
    }

    @Override
    public final AuthResponse login(LoginRequest loginRequest, HttpServletRequest request) {
        log.info("Logging user with data: \n{}", loginRequest);

        String username = loginRequest.username();

        User user = validateUserLoginAccount(username);

        LoginAttempt loginAttempt = user.getLoginAttempt();

        authHelperService.executeUserAuthenticationProcess(loginAttempt, loginRequest);

        loginDeviceFilter.addNewDeviceToUserLoggedInDevices(user, request);

        User updatedUser = jwtService.revokeUserTokens(user);

        return jwtService.buildTokensIntoResponse(updatedUser, TokenRevokeStatus.TO_REVOKE);
    }

    @Override
    public final AuthResponse refreshToken(String refreshToken) {
        log.info("Refresh token : {}", refreshToken);

        User user = validateRefreshTokenData(refreshToken);
        String authToken = jwtService.generateToken(user);

        log.info("New auth token : {}\nFor user : {}", authToken, user);

        jwtService.revokeUserTokens(user);

        return jwtService.buildTokensIntoResponse(authToken, refreshToken);
    }

    @Override
    public final SimpleStringResponse requestResetUsersPassword(String username) {
        log.info("Resetting password for user : {}", username);

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException(USER_NOT_EXIST_MESSAGE)
        );
        log.info("User to gain refresh link : {}", user);

        String passwordRefreshToken = jwtService.generateToken(Collections.emptyMap(),
                user, PASSWORD_REFRESH_TOKEN_TIME);

        log.info("Password refresh link : https://localhost/auth/resetPassword?token={}", passwordRefreshToken);

        return new SimpleStringResponse(username);
    }

    @Override
    public final SimpleStringResponse resetUsersPassword(ResetPasswordRequest request) {
        log.info("Resetting password for user with token : {}", request.resetPasswordToken());

        String username = jwtService.getClaimFromToken(request.resetPasswordToken(), Claims::getSubject)
                .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_EXIST_MESSAGE));

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException(USER_NOT_EXIST_MESSAGE)
        );
        log.info("User to reset password : {}", user);

        authHelperService.executePasswordResetProcess(request, user);

        return new SimpleStringResponse(username);
    }

    private User validateUserLoginAccount(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException(USER_NOT_EXIST_MESSAGE)
        );
        if (!user.isAccountNonLocked()) {
            log.error("User is locked! User : {}", user);

            throw new UsernameNotFoundException("User is locked!");
        }
        return user;
    }

    private void validateUserRegisterData(RegisterRequest registerRequest) {
        Optional<User> existingUser = userRepository.findByUsername(registerRequest.username());

        existingUser.ifPresent(user -> {
            log.error("User already exists! Found user: {}", user);

            throw new UserAlreadyExistsException("User already exists!");
        });
    }

    private User validateRefreshTokenData(String refreshToken) {
        if (refreshToken == null) {
            throw new TokenDoesNotExistException("Token does not exist!");
        }
        Optional<String> usernameOptional = jwtService.getClaimFromToken(refreshToken, Claims::getSubject);

        if (usernameOptional.isEmpty()) {
            throw new UsernameNotFoundException(USER_NOT_EXIST_MESSAGE);
        }
        String username = usernameOptional.get();

        log.info("User of username : {}", username);

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException(USER_NOT_EXIST_MESSAGE)
        );

        if (jwtService.isTokenNotValid(refreshToken)) {
            throw new InvalidTokenException("Token is not valid!");
        }
        return user;
    }
}
