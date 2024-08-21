package razepl.dev.social365.profile.api.profile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import razepl.dev.social365.profile.api.profile.data.BirthdayData;
import razepl.dev.social365.profile.api.profile.data.BirthdayInfoResponse;
import razepl.dev.social365.profile.api.profile.data.ProfileBasicResponse;
import razepl.dev.social365.profile.api.profile.data.ProfilePostResponse;
import razepl.dev.social365.profile.api.profile.data.ProfileQueryResponse;
import razepl.dev.social365.profile.api.profile.data.ProfileRequest;
import razepl.dev.social365.profile.api.profile.data.ProfileResponse;
import razepl.dev.social365.profile.api.profile.data.ProfileSearchResponse;
import razepl.dev.social365.profile.api.profile.data.ProfileSummaryResponse;
import razepl.dev.social365.profile.api.profile.interfaces.ProfileService;
import razepl.dev.social365.profile.config.User;
import razepl.dev.social365.profile.utils.exceptions.ProfileNotFoundException;
import razepl.dev.social365.profile.nodes.about.birthdate.BirthDate;
import razepl.dev.social365.profile.nodes.about.birthdate.BirthDateRepository;
import razepl.dev.social365.profile.nodes.about.mail.Email;
import razepl.dev.social365.profile.nodes.about.mail.interfaces.EmailRepository;
import razepl.dev.social365.profile.nodes.enums.PrivacyLevel;
import razepl.dev.social365.profile.nodes.profile.Profile;
import razepl.dev.social365.profile.nodes.profile.interfaces.ProfileMapper;
import razepl.dev.social365.profile.nodes.profile.interfaces.ProfileRepository;
import razepl.dev.social365.profile.utils.interfaces.ParamValidator;
import razepl.dev.social365.profile.utils.pagination.SocialPage;
import razepl.dev.social365.profile.utils.pagination.SocialPageImpl;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private static final long DEFAULT_PROFILE_PICTURE_ID = 1L;
    private static final int PAGE_SIZE = 3;
    private static final long NO_BANNER_PICTURE_ID = -1L;

    private final ProfileRepository profileRepository;
    private final BirthDateRepository birthDateRepository;
    private final EmailRepository mailRepository;
    private final ProfileMapper profileMapper;
    private final ParamValidator paramValidator;

    @Override
    public ProfileRequest updateProfileBio(User user, String bio) {
        log.info("Updating profile bio for user: {}", user);

        Profile profile = getProfileFromRepository(user.profileId());

        profile.setBio(bio);

        profile = updateProfileInDb(profile);

        return profileMapper.mapProfileToProfileRequest(profile);
    }

    @Override
    public ProfileRequest updateProfilePicture(User user, long profilePictureId) {
        log.info("Updating profile picture for user: {}", user);

        Profile profile = getProfileFromRepository(user.profileId());

        profile.setProfilePictureId(profilePictureId);

        profile = updateProfileInDb(profile);

        return profileMapper.mapProfileToProfileRequest(profile);
    }

    @Override
    public ProfileRequest updateProfileBanner(User user, long profileBannerId) {
        log.info("Updating profile banner for user: {}", user);

        Profile profile = getProfileFromRepository(user.profileId());

        profile.setBannerPictureId(profileBannerId);

        profile = updateProfileInDb(profile);

        return profileMapper.mapProfileToProfileRequest(profile);
    }

    @Override
    public SocialPage<BirthdayInfoResponse> getTodayBirthdays(User user, int pageNumber) {
        log.info("Getting today birthdays for user : {}", user);

        Pageable pageable = PageRequest.of(pageNumber, PAGE_SIZE);

        Page<BirthdayData> birthdayDataPage = birthDateRepository.findTodayBirthdaysByProfileId(user.profileId(), pageable);

        log.info("Found {} birthdays", birthdayDataPage.getNumberOfElements());

        return SocialPageImpl.of(birthdayDataPage.map(profileMapper::mapBirthdayDataToBirthdayInfoResponse));
    }

    @Override
    public SocialPage<ProfileSearchResponse> getProfilesSearchByPattern(String pattern, Pageable pageable) {
        log.info("Getting profiles search by pattern: '{}', with pageable : {}", pattern, pageable);

        Page<Profile> profiles = profileRepository
                .findAllByPatternWithDetails(pattern.toLowerCase(Locale.ROOT), pageable);

        log.info("Found {} search profiles by pattern", profiles.getNumberOfElements());

        return SocialPageImpl.of(profiles.map(profileMapper::mapProfileToProfileSearchResponse));
    }

    @Override
    public SocialPage<ProfileQueryResponse> getProfilesByPattern(String pattern, Pageable pageable) {
        log.info("Getting profiles by pattern: '{}', with pageable : {}", pattern, pageable);

        Page<Profile> profiles = profileRepository.findAllByPattern(pattern.toLowerCase(Locale.ROOT), pageable);

        log.info("Found {} profiles", profiles.getNumberOfElements());

        return SocialPageImpl.of(profiles.map(profileMapper::mapProfileToProfileQueryResponse));
    }

    @Override
    public ProfileBasicResponse getBasicProfileInfoByUsername(String username, String currentUserId) {
        log.info("Getting basic profile info for user with username: {}", username);

        Profile profile = profileRepository.findByUsername(username)
                .orElseThrow(ProfileNotFoundException::new);

        log.info("Profile found : {}", profile);

        return profileMapper.mapProfileToProfileBasicResponse(profile, currentUserId);
    }

    @Override
    public ProfileSummaryResponse getProfileSummary(User user) {
        log.info("Getting profile summary for user : {}", user);

        Profile profile = getProfileFromRepository(user.profileId());

        return profileMapper.mapProfileToProfileSummaryResponse(profile);
    }

    @Override
    public ProfilePostResponse getPostProfileInfo(String profileId) {
        log.info("Getting post profile info for profile with id: {}", profileId);

        Profile profile = getProfileFromRepository(profileId);

        return profileMapper.mapProfileToProfilePostResponse(profile);
    }

    @Override
    @Transactional(transactionManager = "transactionManager")
    public ProfileResponse createUsersProfile(ProfileRequest profileRequest) {
        log.info("Creating profile for user with id: {}", profileRequest.userId());
        log.info("Profile request: {}", profileRequest);

        paramValidator.validateProfileRequest(profileRequest);

        BirthDate birthDate = getBirthDate(profileRequest);

        Email email = getMail(profileRequest);

        Profile profile = Profile
                .builder()
                .userId(profileRequest.userId())
                .email(email)
                .birthDate(birthDate)
                .isOnline(true)
                .firstName(profileRequest.firstName())
                .lastName(profileRequest.lastName())
                .profilePictureId(DEFAULT_PROFILE_PICTURE_ID)
                .bannerPictureId(NO_BANNER_PICTURE_ID)
                .build();

        Profile savedProfile = updateProfileInDb(profile);

        return profileMapper.mapProfileToProfileResponse(savedProfile);
    }

    @Override
    public ProfileResponse getBasicProfileInfo(User user) {
        log.info("Getting basic profile info for user: {}", user);

        Profile profile = getProfileFromRepository(user.profileId());

        return profileMapper.mapProfileToProfileResponse(profile);
    }

    @Override
    public ProfileResponse getProfileInfoByUsername(String username) {
        log.info("Getting profile info for user with username: {}", username);

        Profile profile = profileRepository.findByUsername(username)
                .orElseThrow(ProfileNotFoundException::new);

        log.info("Profile in db found : {}", profile);

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

    private Profile updateProfileInDb(Profile profile) {
        Profile profile1 = profileRepository.save(profile);

        log.info("Saved profile: {}", profile1);

        return profile1;
    }

}
