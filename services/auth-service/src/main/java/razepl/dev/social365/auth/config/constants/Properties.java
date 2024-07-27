package razepl.dev.social365.auth.config.constants;

public final class Properties {

    public static final String EXPIRATION_PROPERTY = "${spring.security.jwt.expiration-time}";

    public static final String REFRESH_PROPERTY = "${spring.security.jwt.refresh-time}";

    public static final String FRONTEND_URL = "${frontend.url}";

    private Properties() {
    }

}
