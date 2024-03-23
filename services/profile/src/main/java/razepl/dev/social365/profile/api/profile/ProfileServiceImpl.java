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
import razepl.dev.social365.profile.nodes.profile.Profile;
import razepl.dev.social365.profile.nodes.profile.interfaces.ProfileMapper;
import razepl.dev.social365.profile.nodes.profile.interfaces.ProfileRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private static final String PROFILE_FOUND_IN_REPOSITORY = "Profile found in repository: {}";
    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;

    @Override
    public final ProfileSummaryResponse getProfileSummary(String userId) {
        log.info("Getting profile summary for user with id: {}", userId);

        Profile profile = profileRepository.findById(userId)
                .orElseThrow(ProfileNotFoundException::new);

        log.info(PROFILE_FOUND_IN_REPOSITORY, profile);

        //TODO: posts count, profile picture url
        return profileMapper.mapProfileToProfileSummaryResponse(profile);
    }

    @Override
    public final ProfilePostResponse getPostProfileInfo(String userId) {
        log.info("Getting post profile info for user with id: {}", userId);

        Profile profile = profileRepository.findById(userId)
                .orElseThrow(ProfileNotFoundException::new);

        log.info(PROFILE_FOUND_IN_REPOSITORY, profile);

        return profileMapper.mapProfileToProfilePostResponse(profile);
    }

    @Override
    public final ProfileResponse createUsersProfile(ProfileRequest profileRequest) {
        return null;
    }

    @Override
    public final ProfileResponse getBasicProfileInfo(String userId) {
        log.info("Getting basic profile info for user with id: {}", userId);

        Profile profile = profileRepository.findById(userId)
                .orElseThrow(ProfileNotFoundException::new);

        log.info(PROFILE_FOUND_IN_REPOSITORY, profile);

        return profileMapper.mapProfileToProfileResponse(profile);
    }
}
