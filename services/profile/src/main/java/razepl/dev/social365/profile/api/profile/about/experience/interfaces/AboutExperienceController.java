package razepl.dev.social365.profile.api.profile.about.experience.interfaces;

import razepl.dev.social365.profile.api.profile.about.experience.data.AboutDetailsRequest;
import razepl.dev.social365.profile.api.profile.about.experience.data.WorkPlaceRequest;
import razepl.dev.social365.profile.api.profile.data.ProfileRequest;

public interface AboutExperienceController {

    ProfileRequest updateProfileWorkPlace(WorkPlaceRequest workPlaceRequest);

    ProfileRequest updateProfileCollege(AboutDetailsRequest educationRequest);

    ProfileRequest updateProfileHighSchool(AboutDetailsRequest highSchoolRequest);

    ProfileRequest deleteProfileHighSchool(String profileId);

    ProfileRequest deleteProfileWorkPlace(String profileId);

    ProfileRequest deleteProfileCollege(String profileId);

}
