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

import java.util.Optional;

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

        Profile profile = profileRepository.findByProfileId(workPlaceRequest.profileId())
                .orElseThrow(ProfileNotFoundException::new);

        log.info("Profile for work place update: {}", profile);

        Optional<Workplace> workplaceOptional = workplaceRepository.findWorkplaceByProfileId(profile.getProfileId());

        if (workplaceOptional.isEmpty()) {
            log.info("Workplace does not exist creating new one.");

            Workplace workplace = Workplace
                    .builder()
                    .workplaceName(workPlaceRequest.workplace())
                    .position(workPlaceRequest.position())
                    .privacyLevel(workPlaceRequest.privacyLevel())
                    .build();

            workplace = workplaceRepository.save(workplace);

            workplaceRepository.createWorkplaceHasRelationship(workplace.getWorkplaceId(), profile.getProfileId());

            log.info("Created workplace: {}", workplace);

        } else {
            log.info("Workplace found updating it.");

            Workplace workplace = workplaceOptional.get();

            workplace.setWorkplaceName(workPlaceRequest.workplace());
            workplace.setPosition(workPlaceRequest.position());
            workplace.setPrivacyLevel(workPlaceRequest.privacyLevel());

            workplace = workplaceRepository.save(workplace);

            log.info("Updated workplace: {}", workplace);
        }
        return profileMapper.mapProfileToProfileRequest(profile);
    }

    @Override
    public final ProfileRequest updateProfileCollege(AboutDetailsRequest educationRequest) {
        log.info("Updating profile education with request: {}", educationRequest);

        Profile profile = getProfileAndValidateRequest(educationRequest, DetailsType.COLLEGE);

        Optional<AboutDetails> collegeOptional = aboutDetailsRepository.findCollegeByProfileId(profile.getProfileId());

        AboutDetails college = updateDetailsObject(educationRequest, collegeOptional, DetailsType.COLLEGE);

        if (collegeOptional.isEmpty()) {
            aboutDetailsRepository.createCollegeStudiedAtRelationship(college.getAboutDetailsId(), profile.getProfileId());
        }
        log.info("Updated college: {}", college);

        return profileMapper.mapProfileToProfileRequest(profile);
    }

    @Override
    public final ProfileRequest updateProfileHighSchool(AboutDetailsRequest highSchoolRequest) {
        log.info("Updating profile high school with request: {}", highSchoolRequest);

        Profile profile = getProfileAndValidateRequest(highSchoolRequest, DetailsType.HIGH_SCHOOL);

        Optional<AboutDetails> highSchoolOptional = aboutDetailsRepository
                .findHighSchoolByProfileId(profile.getProfileId());

        AboutDetails highSchool = updateDetailsObject(highSchoolRequest, highSchoolOptional, DetailsType.HIGH_SCHOOL);

        if (highSchoolOptional.isEmpty()) {
            aboutDetailsRepository.createHighSchoolWentToRelationship(highSchool.getAboutDetailsId(), profile.getProfileId());
        }
        log.info("Updated high school: {}", highSchool);

        return profileMapper.mapProfileToProfileRequest(profile);
    }

    @Override
    public final ProfileRequest deleteProfileHighSchool(String profileId) {
        log.info("Deleting profile high school with profile id: {}", profileId);

        Profile profile = profileRepository.findByProfileId(profileId)
                .orElseThrow(ProfileNotFoundException::new);

        AboutDetails highSchool = aboutDetailsRepository.findHighSchoolByProfileId(profileId)
                .orElseThrow(ProfileDetailsNotFoundException::new);

        log.info("High school to be deleted: {}", highSchool);

        aboutDetailsRepository.delete(highSchool);

        return profileMapper.mapProfileToProfileRequest(profile);
    }

    @Override
    public final ProfileRequest deleteProfileWorkPlace(String profileId) {
        log.info("Deleting profile work place with profile id: {}", profileId);

        Profile profile = profileRepository.findByProfileId(profileId)
                .orElseThrow(ProfileNotFoundException::new);

        Workplace workplace = workplaceRepository.findWorkplaceByProfileId(profileId)
                .orElseThrow(ProfileDetailsNotFoundException::new);

        log.info("Workplace to be deleted: {}", workplace);

        workplaceRepository.delete(workplace);

        return profileMapper.mapProfileToProfileRequest(profile);
    }

    @Override
    public final ProfileRequest deleteProfileCollege(String profileId) {
        log.info("Deleting profile college with profile id: {}", profileId);

        Profile profile = profileRepository.findByProfileId(profileId)
                .orElseThrow(ProfileNotFoundException::new);

        AboutDetails college = aboutDetailsRepository.findCollegeByProfileId(profileId)
                .orElseThrow(ProfileDetailsNotFoundException::new);

        log.info("College to be deleted: {}", college);

        aboutDetailsRepository.delete(college);

        return profileMapper.mapProfileToProfileRequest(profile);
    }

    private AboutDetails updateDetailsObject(AboutDetailsRequest request, Optional<AboutDetails> detailsOptional,
                                             DetailsType detailsType) {
        if (detailsOptional.isEmpty()) {
            log.info("Details of type {} does not exist creating new one.", detailsType);

            AboutDetails details = AboutDetails
                    .builder()
                    .detailsType(detailsType)
                    .propertyValue(request.detailsValue())
                    .privacyLevel(request.privacyLevel())
                    .build();

            details = aboutDetailsRepository.save(details);

            log.info("Created details : {}", details);

            return details;

        }
        log.info("Details of type {} found updating it.", detailsType);

        AboutDetails details = detailsOptional.get();

        details.setPropertyValue(request.detailsValue());
        details.setPrivacyLevel(request.privacyLevel());

        details = aboutDetailsRepository.save(details);

        log.info("Updated details : {}", details);

        return details;
    }

    private Profile getProfileAndValidateRequest(AboutDetailsRequest request, DetailsType detailsType) {
        Profile profile = profileRepository.findByProfileId(request.profileId())
                .orElseThrow(ProfileNotFoundException::new);

        if (request.detailsType() != detailsType) {
            throw new IllegalDetailsTypeException();
        }
        log.info("Profile for update: {}", profile);

        return profile;
    }

}
