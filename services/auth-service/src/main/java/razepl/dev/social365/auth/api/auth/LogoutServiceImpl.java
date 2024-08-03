package razepl.dev.social365.auth.api.auth;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;
import razepl.dev.social365.auth.config.jwt.interfaces.JwtService;
import razepl.dev.social365.auth.entities.user.User;
import razepl.dev.social365.auth.entities.user.interfaces.UserRepository;
import razepl.dev.social365.auth.exceptions.auth.throwable.InvalidTokenException;
import razepl.dev.social365.auth.exceptions.auth.throwable.UserDoesNotExistException;

import java.util.Optional;

import static razepl.dev.social365.auth.config.constants.Headers.AUTH_HEADER;
import static razepl.dev.social365.auth.config.constants.Headers.TOKEN_HEADER;
import static razepl.dev.social365.auth.config.constants.Headers.TOKEN_START_INDEX;


@Slf4j
@Service
@RequiredArgsConstructor
public class LogoutServiceImpl implements LogoutHandler {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    public final void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String authHeader = request.getHeader(AUTH_HEADER);

        if (authHeader == null || !authHeader.startsWith(TOKEN_HEADER)) {
            log.warn("Auth header is null or it does not contain Bearer token");

            response.setStatus(HttpServletResponse.SC_FORBIDDEN);

            return;
        }
        String jwt = authHeader.substring(TOKEN_START_INDEX);

        log.info("Jwt in header : {}", jwt);

        User user = getUserFromToken(jwt);

        long jwtVersion = getJwtVersionFromToken(jwt);

        if (jwtVersion != user.getJwtVersion()) {
            throw new InvalidTokenException("Jwt version is not equal to user's jwt version!");
        }
        log.info("User : {} is logging out!", user.getUsername());

        jwtService.revokeUserTokens(user);

        SecurityContextHolder.clearContext();
    }

    private User getUserFromToken(String jwt) {
        Optional<String> usernameOptional = jwtService.getClaimFromToken(jwt, Claims::getSubject);

        if (usernameOptional.isEmpty()) {
            throw new InvalidTokenException("Username is not present in token!");
        }
        String username = usernameOptional.get();

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserDoesNotExistException(username));
    }

    private long getJwtVersionFromToken(String jwt) {
        Optional<Long> jwtVersionOptional = jwtService.getJwtVersionClaimFromToken(jwt);

        if (jwtVersionOptional.isEmpty()) {
            throw new InvalidTokenException("Jwt version is not present in token!");
        }
        return jwtVersionOptional.get();
    }

}
