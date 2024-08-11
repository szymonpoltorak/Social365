export enum AuthMappings {

    AUTH_MATCHER = "/api/v1/auth",

    REGISTER_MAPPING = AUTH_MATCHER + "/register",

    LOGIN_MAPPING = AUTH_MATCHER + "/login",

    REFRESH_MAPPING = AUTH_MATCHER + "/refreshToken",

    LOGOUT_MAPPING = AUTH_MATCHER + "/logout",

    REQUEST_RESET_PASSWORD_MAPPING = "/forgotPassword",

    RESET_PASSWORD_MAPPING = "/resetPassword",

}
