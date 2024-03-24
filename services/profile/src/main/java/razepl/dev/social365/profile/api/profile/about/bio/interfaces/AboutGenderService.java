package razepl.dev.social365.profile.api.profile.about.bio.interfaces;

import razepl.dev.social365.profile.api.profile.about.old.data.GenderRequest;
import razepl.dev.social365.profile.api.profile.data.ProfileRequest;

public interface AboutGenderService {

    ProfileRequest updateProfileGender(GenderRequest genderRequest);

    ProfileRequest deleteProfileGender(String profileId);

}
