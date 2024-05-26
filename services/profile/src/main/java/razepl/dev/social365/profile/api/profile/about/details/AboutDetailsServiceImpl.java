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
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AboutDetailsServiceImpl implements AboutDetailsService {

    private static final String PROFILE_FOUND_IN_REPOSITORY_FROM_REQUEST = "Profile found in repository from request : {}";
    private static final int MINIMUM_AGE = 13;

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
        Optional<Gender> genderOptional = genderRepository.findByProfileId(genderRequest.profileId());

        if (genderOptional.isEmpty()) {
            log.info("No Gender data found creating new one.");

            Gender gender = Gender
                    .builder()
                    .genderType(genderRequest.gender())
                    .privacyLevel(genderRequest.privacyLevel())
                    .build();

            gender = genderRepository.save(gender);

            genderRepository.createIsGenderRelation(profile.getProfileId(), gender.getGenderId());

            log.info("Created gender : {}", gender);
        } else {
            log.info("Gender found updating it");

            Gender gender = genderOptional.get();

            gender.setGenderType(genderRequest.gender());
            gender.setPrivacyLevel(genderRequest.privacyLevel());

            gender = genderRepository.save(gender);

            log.info("Saved gender : {}", gender);
        }
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

        BirthDate birthDate = dateOfBirthRepository.findByProfileId(dateOfBirthRequest.profileId())
                .orElseThrow(ProfileNotFoundException::new);

        log.info("Birth of date for profile id {} : {}", dateOfBirthRequest.profileId(), birthDate);

        if (Period.between(dateOfBirthRequest.dateOfBirth(), LocalDate.now()).getYears() < MINIMUM_AGE) {
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

        Optional<RelationshipStatus> status = relationshipStatusRepository.findByProfileId(profile.getProfileId());

        if (status.isEmpty()) {
            log.info("No relationship status found creating new one.");

            RelationshipStatus relationshipStatus = RelationshipStatus
                    .builder()
                    .relationshipStatus(relationshipStatusRequest.relationshipStatus())
                    .privacyLevel(relationshipStatusRequest.privacyLevel())
                    .build();

            relationshipStatus = relationshipStatusRepository.save(relationshipStatus);

            relationshipStatusRepository.createIsRelationshipStatusRelation(profile.getProfileId(), relationshipStatus.getRelationshipStatusId());

            log.info("Created relationship status : {}", relationshipStatus);
        } else {
            log.info("Relationship status found updating it");

            RelationshipStatus relationshipStatus = status.get();

            relationshipStatus.setRelationshipStatus(relationshipStatusRequest.relationshipStatus());
            relationshipStatus.setPrivacyLevel(relationshipStatusRequest.privacyLevel());

            relationshipStatus = relationshipStatusRepository.save(relationshipStatus);

            log.info("Saved relationship status : {}", relationshipStatus);
        }

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

        Optional<AboutDetails> currentCityOptional = aboutDetailsRepository
                .findCurrentCityByProfileId(profile.getProfileId());

        AboutDetails currentCity = updateDetailsObject(cityRequest, currentCityOptional, DetailsType.CURRENT_CITY);

        if (currentCityOptional.isEmpty()) {
            aboutDetailsRepository.createLivesInRelation(cityRequest.profileId(), currentCity.getAboutDetailsId());
        }
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

        Optional<AboutDetails> homeTownOptional = aboutDetailsRepository.findHomeTownByProfileId(profile.getProfileId());

        AboutDetails homeTown = updateDetailsObject(homeRequest, homeTownOptional, DetailsType.HOMETOWN);

        if (homeTownOptional.isEmpty()) {
            aboutDetailsRepository.createFromRelation(homeRequest.profileId(), homeTown.getAboutDetailsId());
        }
        log.info("Updated home town : {}", homeTown);

        return profileMapper.mapProfileToProfileRequest(profile);
    }

    @Override
    public final ProfileRequest deleteProfileRelationshipStatus(String profileId) {
        log.info("Deleting relationship status for profile id : {}", profileId);

        Profile profile = getProfileData(profileId);

        RelationshipStatus status = relationshipStatusRepository.findByProfileId(profile.getProfileId())
                .orElseThrow(ProfileDetailsNotFoundException::new);

        relationshipStatusRepository.delete(status);

        log.info("Deleted relationship status : {}", status);

        return profileMapper.mapProfileToProfileRequest(profile);
    }

    @Override
    public final ProfileRequest deleteProfileCurrentCity(String profileId) {
        log.info("Deleting current city for profile id : {}", profileId);

        Profile profile = getProfileData(profileId);

        AboutDetails currentCity = aboutDetailsRepository.findCurrentCityByProfileId(profile.getProfileId())
                .orElseThrow(ProfileDetailsNotFoundException::new);

        aboutDetailsRepository.delete(currentCity);

        log.info("Deleted current city : {}", currentCity);

        return profileMapper.mapProfileToProfileRequest(profile);
    }

    @Override
    public final ProfileRequest deleteProfileHomeTown(String profileId) {
        log.info("Deleting home town for profile id : {}", profileId);

        Profile profile = getProfileData(profileId);

        AboutDetails homeTown = aboutDetailsRepository.findHomeTownByProfileId(profile.getProfileId())
                .orElseThrow(ProfileDetailsNotFoundException::new);

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

}
