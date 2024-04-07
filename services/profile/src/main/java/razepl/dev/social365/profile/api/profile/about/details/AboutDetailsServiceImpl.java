package razepl.dev.social365.profile.api.profile.about.details;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import razepl.dev.social365.profile.api.profile.about.details.interfaces.AboutDetailsService;
import razepl.dev.social365.profile.api.profile.about.experience.data.AboutDetailsRequest;
import razepl.dev.social365.profile.api.profile.about.experience.data.DateOfBirthRequest;
import razepl.dev.social365.profile.api.profile.about.experience.data.GenderRequest;
import razepl.dev.social365.profile.api.profile.about.experience.data.RelationshipStatusRequest;
import razepl.dev.social365.profile.api.profile.data.ProfileRequest;
import razepl.dev.social365.profile.exceptions.IllegalDateException;
import razepl.dev.social365.profile.exceptions.IllegalDetailsTypeException;
import razepl.dev.social365.profile.exceptions.ProfileDetailsNotFoundException;
import razepl.dev.social365.profile.exceptions.ProfileNotFoundException;
import razepl.dev.social365.profile.exceptions.TooYoungForAccountException;
import razepl.dev.social365.profile.nodes.about.birthdate.BirthDate;
import razepl.dev.social365.profile.nodes.about.birthdate.BirthDateRepository;
import razepl.dev.social365.profile.nodes.about.details.AboutDetails;
import razepl.dev.social365.profile.nodes.about.details.enums.DetailsType;
import razepl.dev.social365.profile.nodes.about.details.interfaces.AboutDetailsRepository;
import razepl.dev.social365.profile.nodes.about.gender.Gender;
import razepl.dev.social365.profile.nodes.about.gender.GenderRepository;
import razepl.dev.social365.profile.nodes.about.relationship.RelationshipStatus;
import razepl.dev.social365.profile.nodes.about.relationship.interfaces.RelationshipStatusRepository;
import razepl.dev.social365.profile.nodes.profile.Profile;
import razepl.dev.social365.profile.nodes.profile.interfaces.ProfileMapper;
import razepl.dev.social365.profile.nodes.profile.interfaces.ProfileRepository;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
@RequiredArgsConstructor
public class AboutDetailsServiceImpl implements AboutDetailsService {

    private static final String PROFILE_FOUND_IN_REPOSITORY_FROM_REQUEST = "Profile found in repository from request : {}";

    private final RelationshipStatusRepository relationshipStatusRepository;
    private final ProfileRepository profileRepository;
    private final AboutDetailsRepository aboutDetailsRepository;
    private final GenderRepository genderRepository;
    private final ProfileMapper profileMapper;
    private final BirthDateRepository dateOfBirthRepository;

    @Override
    public final ProfileRequest updateProfileGender(GenderRequest genderRequest) {
        log.info("New gender data : {}", genderRequest);

        Profile profile = getProfileData(genderRequest.profileId());
        Gender gender = profile.getGender();

        log.info("Gender : {}", gender);

        if (gender == null) {
            gender = new Gender();
        }
        gender.setGenderType(genderRequest.gender());
        gender.setPrivacyLevel(genderRequest.privacyLevel());

        genderRepository.save(gender);

        return profileMapper.mapProfileToProfileRequest(profile);
    }

    @Override
    public final ProfileRequest deleteProfileGender(String profileId) {
        log.info("Deleting gender for profile id : {}", profileId);

        Profile profile = getProfileData(profileId);

        genderRepository.deleteById(profile.getGender().getGenderId());

        return profileMapper.mapProfileToProfileRequest(profile);
    }

    @Override
    public final ProfileRequest updateProfileDateOfBirth(DateOfBirthRequest dateOfBirthRequest) {
        log.info("New date of birth data : {}", dateOfBirthRequest);

        BirthDate birthDate = dateOfBirthRepository.findByProfileProfileId(dateOfBirthRequest.profileId())
                .orElseThrow(ProfileNotFoundException::new);

        log.info("Birth of date for profile id {} : {}", dateOfBirthRequest.profileId(), birthDate);

        if (Period.between(dateOfBirthRequest.dateOfBirth(), LocalDate.now()).getYears() < 13) {
            throw new TooYoungForAccountException();
        }
        if (dateOfBirthRequest.dateOfBirth().isAfter(LocalDate.now())) {
            throw new IllegalDateException();
        }
        birthDate.setDateOfBirth(dateOfBirthRequest.dateOfBirth().format(DateTimeFormatter.ISO_LOCAL_DATE));
        birthDate.setPrivacyLevel(dateOfBirthRequest.privacyLevel());

        birthDate = dateOfBirthRepository.save(birthDate);

        return profileMapper.mapProfileToProfileRequest(birthDate.getProfile());
    }

    @Override
    public final ProfileRequest updateProfileRelationshipStatus(RelationshipStatusRequest relationshipStatusRequest) {
        log.info("New relationship status data : {}", relationshipStatusRequest);

        Profile profile = getProfileData(relationshipStatusRequest.profileId());

        RelationshipStatus status = profile.getRelationshipStatus();

        status.setRelationshipStatus(relationshipStatusRequest.relationshipStatus());
        status.setPrivacyLevel(relationshipStatusRequest.privacyLevel());

        status = relationshipStatusRepository.save(status);

        log.info("Updated relationship status : {}", status);

        return profileMapper.mapProfileToProfileRequest(profile);
    }

    @Override
    public final ProfileRequest updateProfileCurrentCity(AboutDetailsRequest cityRequest) {
        log.info("New current city data : {}", cityRequest);

        if (cityRequest.detailsType() != DetailsType.CURRENT_CITY) {
            throw new IllegalDetailsTypeException();
        }
        Profile profile = getProfileData(cityRequest.profileId());
        AboutDetails currentCity = profile.getCurrentCity();

        currentCity = updateDetailsObject(cityRequest, currentCity, DetailsType.CURRENT_CITY);

        log.info("Updated current city : {}", currentCity);

        return profileMapper.mapProfileToProfileRequest(profile);
    }

    @Override
    public final ProfileRequest updateProfileHomeTown(AboutDetailsRequest homeRequest) {
        log.info("New home town data : {}", homeRequest);

        if (homeRequest.detailsType() != DetailsType.HOMETOWN) {
            throw new IllegalDetailsTypeException();
        }
        Profile profile = getProfileData(homeRequest.profileId());
        AboutDetails homeTown = profile.getHomeTown();

        homeTown = updateDetailsObject(homeRequest, homeTown, DetailsType.HOMETOWN);

        log.info("Updated current city : {}", homeTown);

        return profileMapper.mapProfileToProfileRequest(profile);
    }

    @Override
    public final ProfileRequest deleteProfileRelationshipStatus(String profileId) {
        log.info("Deleting relationship status for profile id : {}", profileId);

        Profile profile = getProfileData(profileId);
        RelationshipStatus status = profile.getRelationshipStatus();

        if (status == null) {
            throw new ProfileDetailsNotFoundException();
        }
        relationshipStatusRepository.delete(status);

        log.info("Deleted relationship status : {}", status);

        return profileMapper.mapProfileToProfileRequest(profile);
    }

    @Override
    public final ProfileRequest deleteProfileCurrentCity(String profileId) {
        log.info("Deleting current city for profile id : {}", profileId);

        Profile profile = getProfileData(profileId);

        AboutDetails currentCity = profile.getCurrentCity();

        if (currentCity == null) {
            throw new ProfileDetailsNotFoundException();
        }
        aboutDetailsRepository.delete(currentCity);

        log.info("Deleted current city : {}", currentCity);

        return profileMapper.mapProfileToProfileRequest(profile);
    }

    @Override
    public final ProfileRequest deleteProfileHomeTown(String profileId) {
        log.info("Deleting home town for profile id : {}", profileId);

        Profile profile = getProfileData(profileId);

        AboutDetails homeTown = profile.getHomeTown();

        if (homeTown == null) {
            throw new ProfileDetailsNotFoundException();
        }
        aboutDetailsRepository.delete(homeTown);

        log.info("Deleted home town : {}", homeTown);

        return profileMapper.mapProfileToProfileRequest(profile);
    }

    private Profile getProfileData(String profileId) {
        Profile profile = profileRepository.findByProfileId(profileId)
                .orElseThrow(ProfileNotFoundException::new);

        log.info(PROFILE_FOUND_IN_REPOSITORY_FROM_REQUEST, profile);

        return profile;
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

}
