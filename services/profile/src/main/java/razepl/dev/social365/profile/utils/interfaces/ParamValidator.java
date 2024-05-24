package razepl.dev.social365.profile.utils.interfaces;

import razepl.dev.social365.profile.api.profile.data.ProfileRequest;

@FunctionalInterface
public interface ParamValidator {
    void validateProfileRequest(ProfileRequest profileRequest);
}
