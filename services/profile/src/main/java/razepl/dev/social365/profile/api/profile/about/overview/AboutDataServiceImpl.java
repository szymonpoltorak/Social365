package razepl.dev.social365.profile.api.profile.about.overview;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import razepl.dev.social365.profile.api.profile.about.overview.data.ContactInfoResponse;
import razepl.dev.social365.profile.api.profile.about.overview.data.LocationsResponse;
import razepl.dev.social365.profile.api.profile.about.overview.data.OverviewResponse;
import razepl.dev.social365.profile.api.profile.about.overview.data.WorkEducationResponse;
import razepl.dev.social365.profile.api.profile.about.overview.interfaces.AboutDataService;
import razepl.dev.social365.profile.utils.exceptions.ProfileNotFoundException;
import razepl.dev.social365.profile.nodes.profile.Profile;
import razepl.dev.social365.profile.nodes.profile.interfaces.ProfileMapper;
import razepl.dev.social365.profile.nodes.profile.interfaces.ProfileRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class AboutDataServiceImpl implements AboutDataService {

    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;

    @Override
    public final OverviewResponse getOverview(String username) {
        log.info("Getting overview for username: {}", username);

        Profile profile = getProfile(username);

        return profileMapper.mapProfileToOverviewResponse(profile);
    }

    @Override
    public final WorkEducationResponse getWorkEducation(String username) {
        log.info("Getting work education for username: {}", username);

        Profile profile = getProfile(username);

        return profileMapper.mapProfileToWorkEducationResponse(profile);
    }

    @Override
    public final LocationsResponse getLocations(String username) {
        log.info("Getting locations for username: {}", username);

        Profile profile = getProfile(username);

        return profileMapper.mapProfileToLocationsResponse(profile);
    }

    @Override
    public final ContactInfoResponse getContactInfo(String username) {
        log.info("Getting contact info for username: {}", username);

        Profile profile = getProfile(username);

        return profileMapper.mapProfileToContactInfoResponse(profile);
    }

    private Profile getProfile(String username) {
        Profile profile = profileRepository.findByUsername(username)
                .orElseThrow(ProfileNotFoundException::new);

        log.info("Profile found: {}", profile);

        return profile;
    }
}
