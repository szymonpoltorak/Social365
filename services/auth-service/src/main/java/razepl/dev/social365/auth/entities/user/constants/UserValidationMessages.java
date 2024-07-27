package razepl.dev.social365.auth.entities.user.constants;


public final class UserValidationMessages {
    public static final String NAME_SIZE_MESSAGE = "Name must be between {min} and {max} characters long";

    public static final String NAME_PATTERN_MESSAGE = "Name must contain only alphabetic characters";

    public static final String SURNAME_SIZE_MESSAGE = "Surname must be between {min} and {max} characters long";

    public static final String SURNAME_PATTERN_MESSAGE = "Surname must contain only alphabetic characters";

    public static final String DATE_NULL_MESSAGE = "Date of birth is mandatory";

    public static final String EMAIL_MESSAGE = "Email must be a valid username address";

    public static final String NAME_NULL_MESSAGE = "Name is mandatory";

    public static final String EMAIL_NULL_MESSAGE = "Email is mandatory";

    public static final String SURNAME_NULL_MESSAGE = "Surname is mandatory";

    public static final String PASSWORD_NULL_MESSAGE = "Password is mandatory";

    private UserValidationMessages() {
    }
}
