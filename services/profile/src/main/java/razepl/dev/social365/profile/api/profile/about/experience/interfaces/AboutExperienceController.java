package razepl.dev.social365.profile.api.profile.about.experience.interfaces;

import razepl.dev.social365.profile.api.profile.about.experience.data.AboutDetailsRequest;
import razepl.dev.social365.profile.api.profile.about.experience.data.WorkPlaceRequest;
import razepl.dev.social365.profile.api.profile.data.ProfileRequest;

public interface AboutExperienceController {

    ProfileRequest updateProfileWorkPlace(WorkPlaceRequest workPlaceRequest);

    ProfileRequest updateProfileEducation(AboutDetailsRequest educationRequest);

    ProfileRequest deleteProfileWorkPlace(String profileId);

    ProfileRequest deleteProfileEducation(String profileId);

}
