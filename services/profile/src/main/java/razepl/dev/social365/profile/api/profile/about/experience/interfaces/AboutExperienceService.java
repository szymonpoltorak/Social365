package razepl.dev.social365.profile.api.profile.about.experience.interfaces;

import razepl.dev.social365.profile.api.profile.about.experience.data.AboutDetailsRequest;
import razepl.dev.social365.profile.api.profile.about.experience.data.WorkPlaceRequest;
import razepl.dev.social365.profile.api.profile.data.ProfileRequest;

public interface AboutExperienceService {

    ProfileRequest updateProfileCurrentCity(AboutDetailsRequest cityRequest);

    ProfileRequest updateProfileHomeTown(AboutDetailsRequest cityRequest);

    ProfileRequest updateProfileWorkPlace(WorkPlaceRequest workPlaceRequest);

    ProfileRequest updateProfileEducation(AboutDetailsRequest educationRequest);

    ProfileRequest deleteProfileCurrentCity(String profileId);

    ProfileRequest deleteProfileHomeTown(String profileId);

    ProfileRequest deleteProfileWorkPlace(String profileId);

    ProfileRequest deleteProfileEducation(String profileId);

}
