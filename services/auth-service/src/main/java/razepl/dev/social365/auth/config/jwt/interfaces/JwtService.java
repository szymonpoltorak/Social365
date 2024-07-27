package razepl.dev.social365.auth.config.jwt.interfaces;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import razepl.dev.social365.auth.config.api.auth.data.AuthResponse;
import razepl.dev.social365.auth.config.constants.TokenRevokeStatus;
import razepl.dev.social365.auth.entities.user.User;
import razepl.dev.social365.auth.entities.user.interfaces.ServiceUser;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public interface JwtService {

    Optional<Long> getJwtVersionClaimFromToken(String jwtToken);

    <T> Optional<T> getClaimFromToken(String jwtToken, Function<Claims, T> claimsHandler);

    String generateToken(ServiceUser userDetails);

    String generateToken(Map<String, Object> additionalClaims, ServiceUser userDetails, long expiration);

    boolean isTokenValid(String jwtToken, ServiceUser userDetails);

    Optional<String> getJwtTokenFromRequest(HttpServletRequest request);

    String generateRefreshToken(ServiceUser userDetails);

    AuthResponse buildTokensIntoResponse(String authToken, String refreshToken);

    AuthResponse buildTokensIntoResponse(User user, TokenRevokeStatus shouldBeRevoked);

    User revokeUserTokens(User user);

}
