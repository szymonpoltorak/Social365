package razepl.dev.social365.auth.entities.user;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;
import razepl.dev.social365.auth.entities.user.interfaces.Password;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class PasswordValidator implements ConstraintValidator<Password, String> {
    private static final Pattern PASSWORD_PATTERN = Pattern
            .compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[:\\?\\.@!#:\\-_=+ ])[a-zA-Z0-9:\\?\\.@!#:\\-_=+ ]{8,20}$");

    private static final Pattern LOWER_CASE_PATTERN = Pattern.compile(".*[a-z].*");

    private static final Pattern UPPER_CASE_PATTERN = Pattern.compile(".*[A-Z].*");

    private static final Pattern ONE_DIGIT_PATTERN = Pattern.compile(".*\\d.*");

    private static final Pattern SPECIAL_CHARACTER_PATTERN = Pattern.compile(".*[:\\?\\.@!#:\\-_=+ ].*");

    private static final int MIN_LENGTH = 8;

    private static final int MAX_LENGTH = 20;

    @Override
    public final boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        Matcher matcher = PASSWORD_PATTERN.matcher(value);

        if (matcher.matches()) {
            return true;
        }
        int passwordLength = value.length();

        context.disableDefaultConstraintViolation();

        if (!LOWER_CASE_PATTERN.matcher(value).matches()) {
            context
                    .buildConstraintViolationWithTemplate("Password must contain at least one lowercase letter")
                    .addConstraintViolation();
        }
        if (!UPPER_CASE_PATTERN.matcher(value).matches()) {
            context
                    .buildConstraintViolationWithTemplate("Password must contain at least one uppercase letter")
                    .addConstraintViolation();
        }
        if (!ONE_DIGIT_PATTERN.matcher(value).matches()) {
            context
                    .buildConstraintViolationWithTemplate("Password must contain at least one digit")
                    .addConstraintViolation();
        }
        if (!SPECIAL_CHARACTER_PATTERN.matcher(value).matches()) {
            context
                    .buildConstraintViolationWithTemplate("Password must contain at least one special character [:?.@!#:-_=+ ]")
                    .addConstraintViolation();
        }
        if (passwordLength < MIN_LENGTH || passwordLength > MAX_LENGTH) {
            context
                    .buildConstraintViolationWithTemplate("Password must be between 8 and 20 characters long")
                    .addConstraintViolation();
        }
        return false;
    }
}
