package razepl.dev.social365.auth.config.jwt.interfaces;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import razepl.dev.social365.auth.entities.user.User;
import razepl.dev.social365.auth.entities.user.interfaces.ServiceUser;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public interface JwtService extends OAuth2TokenValidator<Jwt>, JwtDecoder {

    Optional<Long> getJwtVersionClaimFromToken(String jwtToken);

    <T> Optional<T> getClaimFromToken(String jwtToken, Function<Claims, T> claimsHandler);

    Optional<Jwt> getJwtTokenFromRequest(HttpServletRequest request);

    Jwt generateToken(ServiceUser userDetails);

    Jwt generatePasswordRefreshToken(ServiceUser userDetails);

    Jwt generateRefreshToken(ServiceUser userDetails);

    Jwt generateToken(Map<String, Object> additionalClaims, ServiceUser userDetails, long expiration);

    boolean isTokenNotValid(String jwtToken);

    User revokeUserTokens(User user);

    String generateKafkaJwtToken();

}
