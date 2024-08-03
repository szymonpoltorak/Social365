package razepl.dev.social365.auth.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import razepl.dev.social365.auth.api.auth.constants.AuthMappings;
import razepl.dev.social365.auth.api.auth.data.AuthResponse;
import razepl.dev.social365.auth.api.auth.devices.constants.DeviceMappings;
import razepl.dev.social365.auth.config.constants.Headers;
import razepl.dev.social365.auth.config.constants.Matchers;
import razepl.dev.social365.auth.config.constants.Properties;
import razepl.dev.social365.auth.config.constants.TokenRevokeStatus;
import razepl.dev.social365.auth.config.jwt.constants.TokenType;
import razepl.dev.social365.auth.config.jwt.interfaces.JwtService;
import razepl.dev.social365.auth.config.jwt.interfaces.JwtKeyService;
import razepl.dev.social365.auth.entities.user.User;
import razepl.dev.social365.auth.entities.user.interfaces.ServiceUser;
import razepl.dev.social365.auth.entities.user.interfaces.UserRepository;
import razepl.dev.social365.auth.exceptions.auth.throwable.InvalidTokenException;
import razepl.dev.social365.auth.exceptions.auth.throwable.UserDoesNotExistException;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private static final String JWT_VERSION = "ver";
    private static final String TOKEN_TYPE = "typ";
    public static final String TOKEN_ISSUER = "http://auth-service:8080";

    @Value(Properties.EXPIRATION_PROPERTY)
    private long expirationTime;

    @Value(Properties.REFRESH_PROPERTY)
    private long refreshTime;

    private final JwtKeyService jwtKeyService;
    private final UserRepository userRepository;

    @Override
    public final Optional<Long> getJwtVersionClaimFromToken(String jwtToken) {
        return getClaimFromToken(jwtToken, claim -> claim.get(JWT_VERSION, Long.class));
    }

    @Override
    public final <T> Optional<T> getClaimFromToken(String jwtToken, Function<Claims, T> claimsHandler) {
        Claims claims = getAllClaims(jwtToken);

        return Optional.ofNullable(claimsHandler.apply(claims));
    }

    @Override
    public final String generateRefreshToken(ServiceUser userDetails) {
        return buildToken(buildMinimumClaimsForUser(userDetails), userDetails, refreshTime);
    }

    @Override
    public final String generateToken(ServiceUser userDetails) {
        return generateToken(buildMinimumClaimsForUser(userDetails), userDetails, expirationTime);
    }

    @Override
    public final String generateToken(Map<String, Object> additionalClaims, ServiceUser userDetails, long expiration) {
        return buildToken(additionalClaims, userDetails, expiration);
    }

    @Override
    public final boolean isTokenNotValid(String jwtToken) {
        if (!isSignatureValid(jwtToken)) {
            return true;
        }
        String username = getClaimFromToken(jwtToken, Claims::getSubject)
                .orElseThrow(() -> new InvalidTokenException("Token does not have subject in payload!"));

        ServiceUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserDoesNotExistException(username));

        return isTokenExpired(jwtToken, user);
    }

    @Override
    public final Optional<String> getJwtTokenFromRequest(HttpServletRequest request) {
        String authHeader = request.getHeader(Headers.AUTH_HEADER);

        if (!isValidAuthHeader(request)) {
            return Optional.empty();
        }
        return Optional.of(authHeader.substring(Headers.TOKEN_START_INDEX));
    }

    @Override
    public final AuthResponse buildTokensIntoResponse(String authToken, String refreshToken) {
        return buildResponse(authToken, refreshToken);
    }

    @Override
    public final AuthResponse buildTokensIntoResponse(User user, TokenRevokeStatus shouldBeRevoked) {
        String authToken = generateToken(user);
        String refreshToken = generateRefreshToken(user);

        if (shouldBeRevoked == TokenRevokeStatus.TO_REVOKE) {
            revokeUserTokens(user);
        }
        return buildResponse(authToken, refreshToken);
    }

    @Override
    public final User revokeUserTokens(User user) {
        user.incrementJwtVersion();

        User updatedUser =  userRepository.save(user);

        log.info("User with revoked tokens : {}", updatedUser);

        return updatedUser;
    }

    @Override
    public final OAuth2TokenValidatorResult validate(Jwt token) {
        if (isTokenNotValid(token.getTokenValue())) {
            return OAuth2TokenValidatorResult.failure();
        }
        return OAuth2TokenValidatorResult.success();
    }

    private AuthResponse buildResponse(String authToken, String refreshToken) {
        return AuthResponse
                .builder()
                .authToken(authToken)
                .refreshToken(refreshToken)
                .build();
    }

    private boolean isSignatureValid(String jwtToken) {
        Jws<Claims> claimsJws = parseJwsClaims(jwtToken);

        if (!claimsJws.getHeader().get(TOKEN_TYPE).equals(TokenType.JWT.toString())) {
            return false;
        }
        return claimsJws.getHeader().getAlgorithm().equals(Jwts.SIG.RS512.toString());
    }

    private Claims getAllClaims(String token) {
        return parseJwsClaims(token).getPayload();
    }

    private Jws<Claims> parseJwsClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(jwtKeyService.getVerifyKey())
                .build()
                .parseSignedClaims(token);
    }

    private String buildToken(Map<String, Object> additionalClaims, UserDetails userDetails, long expiration) {
        long time = System.currentTimeMillis();

        return Jwts
                .builder()
                .claims(additionalClaims)
                .subject(userDetails.getUsername())
                .header()
                .add(TOKEN_TYPE, TokenType.JWT.name())
                .and()
                .issuer(TOKEN_ISSUER)
                .issuedAt(new Date(time))
                .expiration(new Date(time + expiration))
                .signWith(jwtKeyService.getSignKey())
                .compact();
    }

    private boolean isValidAuthHeader(HttpServletRequest request) {
        String authHeader = request.getHeader(Headers.AUTH_HEADER);
        String servletPath = request.getServletPath();
        List<String> allowedMatchers = List.of(
                DeviceMappings.DEVICE_MAPPING,
                AuthMappings.REQUEST_RESET_PASSWORD_MATCHER,
                AuthMappings.RESET_PASSWORD_MATCHER
        );

        if (authHeader == null || !authHeader.startsWith(Headers.TOKEN_HEADER)) {
            return false;
        }
        if (allowedMatchers.stream().anyMatch(servletPath::contains)) {
            return true;
        }
        return !servletPath.contains(Matchers.AUTH_MAPPING);
    }

    private boolean isTokenExpired(String token, ServiceUser userDetails) {
        if (getExpirationDateFromToken(token).before(new Date())) {
            return true;
        }
        Optional<Long> jwtVersion = getJwtVersionClaimFromToken(token);

        log.info("Jwt version : {}", jwtVersion);

        if (jwtVersion.isEmpty()) {
            throw new InvalidTokenException("Token does not have jwtVersion in payload!");
        }
        long version = jwtVersion.get();

        log.info("User version : {}", userDetails.getJwtVersion());

        return version != userDetails.getJwtVersion();
    }

    private Map<String, Object> buildMinimumClaimsForUser(ServiceUser userDetails) {
        return Map.of(
                JWT_VERSION, userDetails.getJwtVersion()
        );
    }

    private Date getExpirationDateFromToken(String token) {
        Optional<Date> optionalDate = getClaimFromToken(token, Claims::getExpiration);

        if (optionalDate.isEmpty()) {
            throw new InvalidTokenException("Token without expiration date does not exists!");
        }
        return optionalDate.get();
    }

}
