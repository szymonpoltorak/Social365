package razepl.dev.social365.auth.api.auth;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import razepl.dev.social365.auth.api.auth.data.AuthResponse;
import razepl.dev.social365.auth.api.auth.data.LoginRequest;
import razepl.dev.social365.auth.api.auth.data.RegisterRequest;
import razepl.dev.social365.auth.api.auth.data.ResetPasswordRequest;
import razepl.dev.social365.auth.api.auth.data.SimpleStringResponse;
import razepl.dev.social365.auth.api.auth.data.TokenResponse;
import razepl.dev.social365.auth.api.auth.interfaces.AuthHelperService;
import razepl.dev.social365.auth.api.auth.interfaces.AuthService;
import razepl.dev.social365.auth.api.auth.interfaces.LoginDeviceHandler;
import razepl.dev.social365.auth.clients.profile.ProfileService;
import razepl.dev.social365.auth.clients.profile.data.Profile;
import razepl.dev.social365.auth.clients.profile.data.ProfileRequest;
import razepl.dev.social365.auth.config.constants.TokenRevokeStatus;
import razepl.dev.social365.auth.config.jwt.interfaces.JwtService;
import razepl.dev.social365.auth.entities.attempts.LoginAttempt;
import razepl.dev.social365.auth.entities.attempts.interfaces.LoginAttemptRepository;
import razepl.dev.social365.auth.entities.user.User;
import razepl.dev.social365.auth.entities.user.interfaces.UserRepository;
import razepl.dev.social365.auth.exceptions.auth.throwable.InvalidTokenException;
import razepl.dev.social365.auth.exceptions.auth.throwable.TokenDoesNotExistException;
import razepl.dev.social365.auth.exceptions.auth.throwable.UserAccountIsLockedException;
import razepl.dev.social365.auth.exceptions.auth.throwable.UserAlreadyExistsException;
import razepl.dev.social365.auth.exceptions.auth.throwable.UserDoesNotExistException;

import java.time.LocalTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private static final String USER_NOT_EXIST_MESSAGE = "Such user does not exist!";

    private final UserRepository userRepository;
    private final LoginAttemptRepository loginAttemptRepository;
    private final LoginDeviceHandler loginDeviceFilter;
    private final JwtService jwtService;
    private final AuthHelperService authHelperService;
    private final ProfileService profileService;

    @Override
    @Transactional
    public AuthResponse register(RegisterRequest registerRequest, HttpServletRequest request) {
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

        addJwtSecurityContext(newUser);

        Profile profile = profileService.createUsersProfile(ProfileRequest.of(user, registerRequest.dateOfBirth()));

        log.info("Profile created for user : {}", profile);

        loginDeviceFilter.addNewDeviceToUserLoggedInDevices(newUser, request);

        newUser.setProfileId(profile.profileId());

        newUser = userRepository.save(user);

        log.info("Building token response for user : {}", newUser);

        return authHelperService.buildTokensIntoResponse(newUser, profile, TokenRevokeStatus.TO_REVOKE);
    }

    @Override
    public AuthResponse login(LoginRequest loginRequest, HttpServletRequest request) {
        log.info("Logging user with data: \n{}", loginRequest);

        String username = loginRequest.username();

        User user = validateUserLoginAccount(username);

        LoginAttempt loginAttempt = user.getLoginAttempt();

        authHelperService.executeUserAuthenticationProcess(loginAttempt, loginRequest);

        loginDeviceFilter.addNewDeviceToUserLoggedInDevices(user, request);

        addJwtSecurityContext(user);

        Profile profile = profileService.getBasicProfileInfo(user.getProfileId());

        log.info("Profile of user : {}", profile);

        return authHelperService.buildTokensIntoResponse(user, profile, TokenRevokeStatus.TO_REVOKE);
    }

    @Override
    public TokenResponse refreshToken(String refreshToken) {
        log.info("Refresh token : {}", refreshToken);

        User user = validateRefreshTokenData(refreshToken);
        Jwt authToken = jwtService.generateToken(user);

        log.info("New auth token : {}\nFor user : {}", authToken, user);

        jwtService.revokeUserTokens(user);

        return authHelperService.buildTokensIntoResponse(authToken.getTokenValue(), refreshToken);
    }

    @Override
    public SimpleStringResponse requestResetUsersPassword(String username) {
        log.info("Resetting password for user : {}", username);

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UserDoesNotExistException(username)
        );
        log.info("User to gain refresh link : {}", user);

        Jwt passwordRefreshToken = jwtService.generatePasswordRefreshToken(user);

        log.info("Password refresh link : https://localhost/auth/resetPassword?token={}", passwordRefreshToken);

        return new SimpleStringResponse(username);
    }

    @Override
    public SimpleStringResponse resetUsersPassword(ResetPasswordRequest request) {
        log.info("Resetting password for user with token : {}", request.resetPasswordToken());

        String username = jwtService.getClaimFromToken(request.resetPasswordToken(), Claims::getSubject)
                .orElseThrow(() -> new InvalidTokenException(USER_NOT_EXIST_MESSAGE));

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UserDoesNotExistException(username)
        );
        log.info("User to reset password : {}", user);

        authHelperService.executePasswordResetProcess(request, user);

        return new SimpleStringResponse(username);
    }

    private User validateUserLoginAccount(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UserDoesNotExistException(username)
        );
        if (!user.isAccountNonLocked()) {
            log.error("User is locked! User : {}", user);

            throw new UserAccountIsLockedException("User is locked!");
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
            throw new InvalidTokenException(USER_NOT_EXIST_MESSAGE);
        }
        String username = usernameOptional.get();

        log.info("User of username : {}", username);

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UserDoesNotExistException(username)
        );

        if (jwtService.isTokenNotValid(refreshToken)) {
            throw new InvalidTokenException("Token is not valid!");
        }
        return user;
    }

    private void addJwtSecurityContext(User user) {
        Jwt jwt = jwtService.generateToken(user);

        SecurityContextHolder.getContext().setAuthentication(new JwtAuthenticationToken(jwt));
    }

}
