package razepl.dev.social365.profile.api.profile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import razepl.dev.social365.profile.api.profile.data.ProfilePostResponse;
import razepl.dev.social365.profile.api.profile.data.ProfileRequest;
import razepl.dev.social365.profile.api.profile.data.ProfileResponse;
import razepl.dev.social365.profile.api.profile.data.ProfileSummaryResponse;
import razepl.dev.social365.profile.api.profile.interfaces.ProfileService;
import razepl.dev.social365.profile.exceptions.ProfileNotFoundException;
import razepl.dev.social365.profile.nodes.about.birthdate.BirthDate;
import razepl.dev.social365.profile.nodes.about.birthdate.BirthDateRepository;
import razepl.dev.social365.profile.nodes.about.mail.Email;
import razepl.dev.social365.profile.nodes.about.mail.interfaces.EmailRepository;
import razepl.dev.social365.profile.nodes.enums.PrivacyLevel;
import razepl.dev.social365.profile.nodes.profile.Profile;
import razepl.dev.social365.profile.nodes.profile.interfaces.ProfileMapper;
import razepl.dev.social365.profile.nodes.profile.interfaces.ProfileRepository;
import razepl.dev.social365.profile.utils.interfaces.ParamValidator;

import java.time.format.DateTimeFormatter;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private static final long DEFAULT_PROFILE_PICTURE_ID = 1L;

    private final ProfileRepository profileRepository;
    private final BirthDateRepository birthDateRepository;
    private final EmailRepository mailRepository;
    private final ProfileMapper profileMapper;
    private final ParamValidator paramValidator;

    @Override
    public final ProfileRequest updateProfileBio(String profileId, String bio) {
        log.info("Updating profile bio for profileId: {}", profileId);

        Profile profile = getProfileFromRepository(profileId);

        profile.setBio(bio);

        profile = profileRepository.save(profile);

        return profileMapper.mapProfileToProfileRequest(profile);
    }

    @Override
    public final ProfileSummaryResponse getProfileSummary(String profileId) {
        log.info("Getting profile summary for user with id: {}", profileId);

        Profile profile = getProfileFromRepository(profileId);

        return profileMapper.mapProfileToProfileSummaryResponse(profile);
    }

    @Override
    public final ProfilePostResponse getPostProfileInfo(String profileId) {
        log.info("Getting post profile info for profile with id: {}", profileId);

        Profile profile = getProfileFromRepository(profileId);

        return profileMapper.mapProfileToProfilePostResponse(profile);
    }

    @Override
    public final ProfileResponse createUsersProfile(ProfileRequest profileRequest) {
        log.info("Creating profile for user with id: {}", profileRequest.userId());
        log.info("Profile request: {}", profileRequest);

        paramValidator.validateProfileRequest(profileRequest);

        BirthDate birthDate = getBirthDate(profileRequest);

        Email email = getMail(profileRequest);

        Profile profile = Profile
                .builder()
                .userId(profileRequest.userId())
                .email(email)
                .firstName(profileRequest.firstName())
                .lastName(profileRequest.lastName())
                .birthDate(birthDate)
                .profilePictureId(DEFAULT_PROFILE_PICTURE_ID)
                .build();
        Profile savedProfile = profileRepository.save(profile);

        mailRepository.createEmailHasRelation(email.getMailId(), savedProfile.getProfileId());

        birthDateRepository.createBornOnRelation(birthDate.getBirthDateId(), savedProfile.getProfileId());

        log.info("Saved profile: {}", savedProfile);

        return profileMapper.mapProfileToProfileResponse(savedProfile);
    }

    @Override
    public final ProfileResponse getBasicProfileInfo(String profileId) {
        log.info("Getting basic profile info for profile with id: {}", profileId);

        Profile profile = getProfileFromRepository(profileId);

        return profileMapper.mapProfileToProfileResponse(profile);
    }

    private Profile getProfileFromRepository(String profileId) {
        Profile profile = profileRepository.findByProfileId(profileId)
                .orElseThrow(ProfileNotFoundException::new);

        log.info("Profile found in repository: {}", profile);

        return profile;
    }

    private Email getMail(ProfileRequest profileRequest) {
        Email email = Email
                .builder()
                .emailValue(profileRequest.username())
                .privacyLevel(PrivacyLevel.ONLY_ME)
                .build();
        Email savedEmail = mailRepository.save(email);

        log.info("Saved mail: {}", savedEmail);

        return savedEmail;
    }

    private BirthDate getBirthDate(ProfileRequest profileRequest) {
        BirthDate birthDate = BirthDate
                .builder()
                .dateOfBirth(profileRequest.dateOfBirth().format(DateTimeFormatter.ISO_LOCAL_DATE))
                .privacyLevel(PrivacyLevel.ONLY_ME)
                .build();
        BirthDate savedBirthDate = birthDateRepository.save(birthDate);

        log.info("Saved birth date: {}", savedBirthDate);

        return savedBirthDate;
    }

}
