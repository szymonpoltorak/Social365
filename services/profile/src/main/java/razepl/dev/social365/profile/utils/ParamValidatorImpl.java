package razepl.dev.social365.profile.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import razepl.dev.social365.profile.api.profile.data.ProfileRequest;
import razepl.dev.social365.profile.utils.exceptions.InvalidEmailException;
import razepl.dev.social365.profile.utils.exceptions.InvalidParamException;
import razepl.dev.social365.profile.utils.exceptions.TooYoungForAccountException;
import razepl.dev.social365.profile.utils.exceptions.UsernameAlreadyClaimedException;
import razepl.dev.social365.profile.nodes.profile.interfaces.ProfileRepository;
import razepl.dev.social365.profile.utils.interfaces.ParamValidator;

import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class ParamValidatorImpl implements ParamValidator {

    private static final int MINIMAL_AGE = 13;
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^(.+)@(.+){3,100}$");
    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z]{2,100}$");

    private final ProfileRepository profileRepository;

    @Override
    public final void validateProfileRequest(ProfileRequest profileRequest) {
        if (profileRepository.existsByUsername(profileRequest.username())) {
            throw new UsernameAlreadyClaimedException(profileRequest.username());
        }
        if (Period.between(profileRequest.dateOfBirth(), LocalDate.now()).getYears() < MINIMAL_AGE) {
            throw new TooYoungForAccountException();
        }
        if (!EMAIL_PATTERN.matcher(profileRequest.username()).matches()) {
            throw new InvalidEmailException("Invalid email format");
        }
        if (!NAME_PATTERN.matcher(profileRequest.firstName()).matches()) {
            throw new InvalidParamException("Invalid first name format");
        }
        if (!NAME_PATTERN.matcher(profileRequest.lastName()).matches()) {
            throw new InvalidParamException("Invalid last name format");
        }
    }
}
