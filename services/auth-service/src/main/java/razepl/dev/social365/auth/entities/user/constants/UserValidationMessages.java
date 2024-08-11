package razepl.dev.social365.auth.entities.user.constants;


public final class UserValidationMessages {
    public static final String FIRST_NAME_SIZE_MESSAGE = "First Name must be between {min} and {max} characters long";

    public static final String FIRST_NAME_PATTERN_MESSAGE = "First Name must contain only alphabetic characters";

    public static final String LAST_NAME_SIZE_MESSAGE = "Last Name must be between {min} and {max} characters long";

    public static final String LAST_NAME_PATTERN_MESSAGE = "Last Name must contain only alphabetic characters";

    public static final String EMAIL_MESSAGE = "Email must be a valid username address";

    public static final String FIRST_NAME_NULL_MESSAGE = "First Name is mandatory";

    public static final String EMAIL_NULL_MESSAGE = "Email is mandatory";

    public static final String LAST_NAME_NULL_MESSAGE = "Last Name is mandatory";

    public static final String PASSWORD_NULL_MESSAGE = "Password is mandatory";

    private UserValidationMessages() {
    }
}
