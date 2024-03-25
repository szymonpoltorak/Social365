package razepl.dev.social365.profile.api.profile.about.experience;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import razepl.dev.social365.profile.api.profile.about.experience.data.AboutDetailsRequest;
import razepl.dev.social365.profile.api.profile.about.experience.data.WorkPlaceRequest;
import razepl.dev.social365.profile.api.profile.about.experience.interfaces.AboutExperienceService;
import razepl.dev.social365.profile.api.profile.data.ProfileRequest;
import razepl.dev.social365.profile.exceptions.IllegalDetailsTypeException;
import razepl.dev.social365.profile.exceptions.ProfileDetailsNotFoundException;
import razepl.dev.social365.profile.exceptions.ProfileNotFoundException;
import razepl.dev.social365.profile.nodes.about.details.AboutDetails;
import razepl.dev.social365.profile.nodes.about.details.enums.DetailsType;
import razepl.dev.social365.profile.nodes.about.details.interfaces.AboutDetailsRepository;
import razepl.dev.social365.profile.nodes.about.workplace.Workplace;
import razepl.dev.social365.profile.nodes.about.workplace.interfaces.WorkplaceRepository;
import razepl.dev.social365.profile.nodes.profile.Profile;
import razepl.dev.social365.profile.nodes.profile.interfaces.ProfileMapper;
import razepl.dev.social365.profile.nodes.profile.interfaces.ProfileRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class AboutExperienceServiceImpl implements AboutExperienceService {

    private final ProfileRepository profileRepository;
    private final WorkplaceRepository workplaceRepository;
    private final AboutDetailsRepository aboutDetailsRepository;
    private final ProfileMapper profileMapper;

    @Override
    public final ProfileRequest updateProfileWorkPlace(WorkPlaceRequest workPlaceRequest) {
        log.info("Updating profile work place with request: {}", workPlaceRequest);

        Profile profile = profileRepository.findById(workPlaceRequest.profileId())
                .orElseThrow(ProfileNotFoundException::new);

        log.info("Profile for work place update: {}", profile);

        Workplace workplace = profile.getWorkplace();

        log.info("Workplace: {}", workplace);

        if (workplace == null) {
            workplace = new Workplace();
        }
        workplace.setPosition(workPlaceRequest.position());
        workplace.setPrivacyLevel(workPlaceRequest.privacyLevel());
        workplace.setWorkplaceName(workPlaceRequest.workplace());

        workplace = workplaceRepository.save(workplace);

        log.info("Updated workplace: {}", workplace);

        return profileMapper.mapProfileToProfileRequest(profile);
    }

    @Override
    public final ProfileRequest updateProfileCollege(AboutDetailsRequest educationRequest) {
        log.info("Updating profile education with request: {}", educationRequest);

        Profile profile = getProfileAndValidateRequest(educationRequest, DetailsType.COLLEGE);

        AboutDetails college = profile.getCollege();

        log.info("College: {}", college);

        college = updateDetailsObject(educationRequest, college, DetailsType.COLLEGE);

        log.info("Updated college: {}", college);

        return profileMapper.mapProfileToProfileRequest(profile);
    }

    @Override
    public final ProfileRequest updateProfileHighSchool(AboutDetailsRequest highSchoolRequest) {
        log.info("Updating profile high school with request: {}", highSchoolRequest);

        Profile profile = getProfileAndValidateRequest(highSchoolRequest, DetailsType.HIGH_SCHOOL);

        AboutDetails highSchool = profile.getHighSchool();

        log.info("High school: {}", highSchool);

        highSchool = updateDetailsObject(highSchoolRequest, highSchool, DetailsType.HIGH_SCHOOL);

        log.info("Updated high school: {}", highSchool);

        return profileMapper.mapProfileToProfileRequest(profile);
    }

    @Override
    public final ProfileRequest deleteProfileHighSchool(String profileId) {
        log.info("Deleting profile high school with profile id: {}", profileId);

        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(ProfileNotFoundException::new);

        AboutDetails highSchool = profile.getHighSchool();

        log.info("High school to be deleted: {}", highSchool);

        if (highSchool == null) {
            throw new ProfileDetailsNotFoundException();
        }
        aboutDetailsRepository.delete(highSchool);

        return profileMapper.mapProfileToProfileRequest(profile);
    }

    @Override
    public final ProfileRequest deleteProfileWorkPlace(String profileId) {
        log.info("Deleting profile work place with profile id: {}", profileId);

        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(ProfileNotFoundException::new);

        Workplace workplace = profile.getWorkplace();

        log.info("Workplace to be deleted: {}", workplace);

        if (workplace == null) {
            throw new ProfileDetailsNotFoundException();
        }
        workplaceRepository.delete(workplace);

        return profileMapper.mapProfileToProfileRequest(profile);
    }

    @Override
    public final ProfileRequest deleteProfileCollege(String profileId) {
        log.info("Deleting profile college with profile id: {}", profileId);

        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(ProfileNotFoundException::new);

        AboutDetails college = profile.getCollege();

        log.info("College to be deleted: {}", college);

        if (college == null) {
            throw new ProfileDetailsNotFoundException();
        }
        aboutDetailsRepository.delete(college);

        return profileMapper.mapProfileToProfileRequest(profile);
    }

    private AboutDetails updateDetailsObject(AboutDetailsRequest request, AboutDetails details,
                                             DetailsType detailsType) {
        if (details == null) {
            details = AboutDetails.builder()
                    .detailsType(detailsType)
                    .build();
        }
        details.setPrivacyLevel(request.privacyLevel());
        details.setPropertyValue(request.detailsValue());

        return aboutDetailsRepository.save(details);
    }

    private Profile getProfileAndValidateRequest(AboutDetailsRequest request, DetailsType detailsType) {
        if (request.detailsType() != detailsType) {
            throw new IllegalDetailsTypeException();
        }
        Profile profile = profileRepository.findById(request.profileId())
                .orElseThrow(ProfileNotFoundException::new);

        log.info("Profile for update: {}", profile);

        return profile;
    }

}
