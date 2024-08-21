package razepl.dev.social365.auth.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.stereotype.Service;
import razepl.dev.social365.auth.api.auth.constants.AuthMappings;
import razepl.dev.social365.auth.api.auth.devices.constants.DeviceMappings;
import razepl.dev.social365.auth.config.constants.Headers;
import razepl.dev.social365.auth.config.constants.Matchers;
import razepl.dev.social365.auth.config.constants.Properties;
import razepl.dev.social365.auth.config.jwt.constants.JwtClaims;
import razepl.dev.social365.auth.config.jwt.constants.TokenType;
import razepl.dev.social365.auth.config.jwt.interfaces.JwtKeyService;
import razepl.dev.social365.auth.config.jwt.interfaces.JwtService;
import razepl.dev.social365.auth.entities.user.User;
import razepl.dev.social365.auth.entities.user.interfaces.ServiceUser;
import razepl.dev.social365.auth.entities.user.interfaces.UserRepository;
import razepl.dev.social365.auth.utils.exceptions.InvalidTokenException;
import razepl.dev.social365.auth.utils.exceptions.UserDoesNotExistException;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Slf4j
@Service
public class JwtServiceImpl implements JwtService {

    private static final long PASSWORD_REFRESH_TOKEN_TIME = 600_000L;

    private final long expirationTime;
    private final long refreshTime;
    private final JwtKeyService jwtKeyService;
    private final UserRepository userRepository;
    private final JwtDecoder jwtDecoder;

    public JwtServiceImpl(JwtKeyService jwtKeyService, UserRepository userRepository,
                          @Value(Properties.REFRESH_PROPERTY) long refreshTime,
                          @Value(Properties.EXPIRATION_PROPERTY) long expirationTime) {
        this.jwtKeyService = jwtKeyService;
        this.userRepository = userRepository;
        this.refreshTime = refreshTime;
        this.expirationTime = expirationTime;
        this.jwtDecoder = getJwtDecoder();
    }

    @Override
    public final User revokeUserTokens(User user) {
        user.incrementJwtVersion();

        User updatedUser = userRepository.save(user);

        log.info("User with revoked tokens : {}", updatedUser);

        return updatedUser;
    }

    @Override
    public final Jwt decode(String token) throws JwtException {
        return jwtDecoder.decode(token);
    }

    @Override
    public final Optional<Long> getJwtVersionClaimFromToken(String jwtToken) {
        return getClaimFromToken(jwtToken, claim -> claim.get(JwtClaims.JWT_VERSION, Long.class));
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
    public final <T> Optional<T> getClaimFromToken(String jwtToken, Function<Claims, T> claimsHandler) {
        Claims claims = getAllClaims(jwtToken);

        return Optional.ofNullable(claimsHandler.apply(claims));
    }

    @Override
    public final Jwt generateRefreshToken(ServiceUser userDetails) {
        return decode(buildToken(buildMinimumClaimsForUser(userDetails), userDetails, refreshTime));
    }

    @Override
    public final Jwt generateToken(ServiceUser userDetails) {
        return generateToken(buildMinimumClaimsForUser(userDetails), userDetails, expirationTime);
    }

    @Override
    public final Jwt generatePasswordRefreshToken(ServiceUser userDetails) {
        Map<String, Object> claims = new HashMap<>(buildMinimumClaimsForUser(userDetails));

        claims.put(JwtClaims.PASSWORD_RESET, true);

        return generateToken(claims, userDetails, PASSWORD_REFRESH_TOKEN_TIME);
    }

    @Override
    public final Jwt generateToken(Map<String, Object> additionalClaims, ServiceUser userDetails, long expiration) {
        return decode(buildToken(additionalClaims, userDetails, expiration));
    }

    @Override
    public final Optional<Jwt> getJwtTokenFromRequest(HttpServletRequest request) {
        String authHeader = request.getHeader(Headers.AUTH_HEADER);

        if (!isValidAuthHeader(request)) {
            return Optional.empty();
        }
        return Optional.ofNullable(decode(authHeader.substring(Headers.TOKEN_START_INDEX)));
    }

    @Override
    public final OAuth2TokenValidatorResult validate(Jwt token) {
        if (isTokenNotValid(token.getTokenValue())) {
            return OAuth2TokenValidatorResult.failure();
        }
        return OAuth2TokenValidatorResult.success();
    }

    private JwtDecoder getJwtDecoder() {
        NimbusJwtDecoder decoder = (NimbusJwtDecoder) OAuth2AuthorizationServerConfiguration
                .jwtDecoder(jwtKeyService.getJwkSource());

        decoder.setJwtValidator(this);

        return decoder;
    }

    private boolean isSignatureValid(CharSequence jwtToken) {
        Jws<Claims> claimsJws = parseJwsClaims(jwtToken);

        if (!claimsJws.getHeader().get(JwtClaims.TOKEN_TYPE).equals(TokenType.JWT.toString())) {
            return false;
        }
        return claimsJws.getHeader().getAlgorithm().equals(Jwts.SIG.RS512.toString());
    }

    private Claims getAllClaims(CharSequence token) {
        return parseJwsClaims(token).getPayload();
    }

    private Jws<Claims> parseJwsClaims(CharSequence token) {
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
                .add(JwtClaims.TOKEN_TYPE, TokenType.JWT.name())
                .and()
                .issuer(JwtClaims.TOKEN_ISSUER)
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
        return servletPath.contains(Matchers.AUTH_MAPPING);
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
                JwtClaims.JWT_VERSION, userDetails.getJwtVersion(),
                JwtClaims.USER_ID, userDetails.getUserId(),
                JwtClaims.PROFILE_ID, userDetails.getProfileId() == null ? "NO_PROFILE_ID" : userDetails.getProfileId()
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
