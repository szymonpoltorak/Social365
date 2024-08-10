package razepl.dev.social365.auth.api.auth.constants;

public final class AuthMappings {

    public static final String REGISTER_MAPPING = "/register";

    public static final String LOGIN_MAPPING = "/login";

    public static final String REFRESH_MAPPING = "/refreshToken";

    public static final String REQUEST_RESET_PASSWORD_MAPPING = "/forgotPassword";

    public static final String RESET_PASSWORD_MAPPING = "/resetPassword";

    public static final String RESET_PASSWORD_MATCHER = "/api/v1/auth/resetPassword";

    public static final String REQUEST_RESET_PASSWORD_MATCHER = "/api/v1/auth/forgotPassword";

    private AuthMappings() {
    }
}
