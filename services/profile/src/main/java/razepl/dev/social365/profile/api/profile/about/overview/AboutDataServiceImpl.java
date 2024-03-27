package razepl.dev.social365.profile.api.profile.about.overview;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import razepl.dev.social365.profile.api.profile.about.overview.data.ContactInfoResponse;
import razepl.dev.social365.profile.api.profile.about.overview.data.LocationsResponse;
import razepl.dev.social365.profile.api.profile.about.overview.data.OverviewResponse;
import razepl.dev.social365.profile.api.profile.about.overview.data.WorkEducationResponse;
import razepl.dev.social365.profile.api.profile.about.overview.interfaces.AboutDataService;
import razepl.dev.social365.profile.exceptions.ProfileNotFoundException;
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
    public final OverviewResponse getOverview(String profileId) {
        log.info("Getting overview for profileId: {}", profileId);

        Profile profile = getProfile(profileId);

        return profileMapper.mapProfileToOverviewResponse(profile);
    }

    @Override
    public final WorkEducationResponse getWorkEducation(String profileId) {
        log.info("Getting work education for profileId: {}", profileId);

        Profile profile = getProfile(profileId);

        return profileMapper.mapProfileToWorkEducationResponse(profile);
    }

    @Override
    public final LocationsResponse getLocations(String profileId) {
        log.info("Getting locations for profileId: {}", profileId);

        Profile profile = getProfile(profileId);

        return profileMapper.mapProfileToLocationsResponse(profile);
    }

    @Override
    public final ContactInfoResponse getContactInfo(String profileId) {
        log.info("Getting contact info for profileId: {}", profileId);

        Profile profile = getProfile(profileId);

        return profileMapper.mapProfileToContactInfoResponse(profile);
    }

    private Profile getProfile(String profileId) {
        Profile profile = profileRepository.findByProfileId(profileId)
                .orElseThrow(ProfileNotFoundException::new);

        log.info("Profile found: {}", profile);

        return profile;
    }
}
