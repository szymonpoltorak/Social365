package razepl.dev.social365.profile.api.profile.about.experience.interfaces;

import razepl.dev.social365.profile.api.profile.about.experience.data.AboutDetailsRequest;
import razepl.dev.social365.profile.api.profile.about.experience.data.WorkPlaceRequest;
import razepl.dev.social365.profile.api.profile.data.ProfileRequest;
import razepl.dev.social365.profile.config.User;

public interface AboutExperienceService {

    ProfileRequest updateProfileWorkPlace(User user, WorkPlaceRequest workPlaceRequest);

    ProfileRequest updateProfileCollege(User user, AboutDetailsRequest educationRequest);

    ProfileRequest updateProfileHighSchool(User user, AboutDetailsRequest highSchoolRequest);

    ProfileRequest deleteProfileHighSchool(String profileId);

    ProfileRequest deleteProfileWorkPlace(String profileId);

    ProfileRequest deleteProfileCollege(String profileId);

}
