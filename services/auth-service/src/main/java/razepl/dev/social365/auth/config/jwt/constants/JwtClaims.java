package razepl.dev.social365.auth.config.jwt.constants;

public final class JwtClaims {

    public static final String USER_ID = "userId";
    public static final String JWT_VERSION = "ver";
    public static final String TOKEN_TYPE = "typ";
    public static final String TOKEN_ISSUER = "http://auth-service:8080";
    public static final String PROFILE_ID = "profileId";
    public static final String ISSUER = "iss";
    public static final String SUBJECT = "sub";
    public static final String AUDIENCE = "aud";
    public static final String EXPIRATION = "exp";
    public static final String NOT_BEFORE = "nbf";
    public static final String ISSUED_AT = "iat";
    public static final String ID = "jti";
    public static final String PASSWORD_RESET = "passwordReset";

    private JwtClaims() {
    }

}
