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
import razepl.dev.social365.profile.exceptions.ProfileNotFoundException;
import razepl.dev.social365.profile.exceptions.TooYoungForAccountException;
import razepl.dev.social365.profile.nodes.about.birthdate.BirthDate;
import razepl.dev.social365.profile.nodes.about.birthdate.BirthDateRepository;
import razepl.dev.social365.profile.nodes.about.gender.Gender;
import razepl.dev.social365.profile.nodes.about.gender.GenderRepository;
import razepl.dev.social365.profile.nodes.profile.Profile;
import razepl.dev.social365.profile.nodes.profile.interfaces.ProfileMapper;
import razepl.dev.social365.profile.nodes.profile.interfaces.ProfileRepository;

import java.time.LocalDate;
import java.time.Period;

@Slf4j
@Service
@RequiredArgsConstructor
public class AboutDetailsServiceImpl implements AboutDetailsService {

    private static final String PROFILE_FOUND_IN_REPOSITORY_FROM_REQUEST = "Profile found in repository from request : {}";

    private final ProfileRepository profileRepository;
    private final GenderRepository genderRepository;
    private final ProfileMapper profileMapper;
    private final BirthDateRepository dateOfBirthRepository;

    @Override
    public final ProfileRequest updateProfileGender(GenderRequest genderRequest) {
        log.info("New gender data : {}", genderRequest);

        Profile profile = profileRepository.findById(genderRequest.profileId())
                .orElseThrow(ProfileNotFoundException::new);

        log.info(PROFILE_FOUND_IN_REPOSITORY_FROM_REQUEST, profile);

        Gender gender = profile.getGender();

        gender.setGenderType(genderRequest.gender());
        gender.setPrivacyLevel(genderRequest.privacyLevel());

        genderRepository.save(gender);

        return profileMapper.mapProfileToProfileRequest(profile);
    }

    @Override
    public final ProfileRequest deleteProfileGender(String profileId) {
        log.info("Deleting gender for profile id : {}", profileId);

        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(ProfileNotFoundException::new);

        log.info(PROFILE_FOUND_IN_REPOSITORY_FROM_REQUEST, profile);

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
        birthDate.setDateOfBirth(dateOfBirthRequest.dateOfBirth());
        birthDate.setPrivacyLevel(dateOfBirthRequest.privacyLevel());

        birthDate = dateOfBirthRepository.save(birthDate);

        return profileMapper.mapProfileToProfileRequest(birthDate.getProfile());
    }

    @Override
    public final ProfileRequest updateProfileRelationshipStatus(RelationshipStatusRequest relationshipStatusRequest) {
        return null;
    }

    @Override
    public final ProfileRequest deleteProfileRelationshipStatus(String profileId) {
        return null;
    }

    @Override
    public final ProfileRequest deleteProfileCurrentCity(String profileId) {
        return null;
    }

    @Override
    public final ProfileRequest deleteProfileHomeTown(String profileId) {
        return null;
    }

    @Override
    public final ProfileRequest updateProfileCurrentCity(AboutDetailsRequest cityRequest) {
        return null;
    }

    @Override
    public final ProfileRequest updateProfileHomeTown(AboutDetailsRequest cityRequest) {
        return null;
    }

}
