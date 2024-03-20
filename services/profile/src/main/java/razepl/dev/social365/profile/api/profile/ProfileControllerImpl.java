package razepl.dev.social365.profile.api.profile;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import razepl.dev.social365.profile.api.profile.constants.ProfileMappings;
import razepl.dev.social365.profile.api.profile.data.ProfilePostResponse;
import razepl.dev.social365.profile.api.profile.data.ProfileRequest;
import razepl.dev.social365.profile.api.profile.data.ProfileResponse;
import razepl.dev.social365.profile.api.profile.data.ProfileSummaryResponse;
import razepl.dev.social365.profile.api.profile.interfaces.ProfileController;
import razepl.dev.social365.profile.api.profile.interfaces.ProfileService;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = ProfileMappings.PROFILE_API_MAPPING)
public class ProfileControllerImpl implements ProfileController {

    private final ProfileService profileService;

    @Override
    @GetMapping(value = ProfileMappings.GET_PROFILE_SUMMARY_MAPPING)
    public final ProfileSummaryResponse getProfileSummary(@RequestParam String username) {
        return profileService.getProfileSummary(username);
    }

    @Override
    @GetMapping(value = ProfileMappings.GET_POST_PROFILE_INFO_MAPPING)
    public final ProfilePostResponse getPostProfileInfo(@RequestParam String username) {
        return profileService.getPostProfileInfo(username);
    }

    @Override
    @PostMapping(value = ProfileMappings.CREATE_USERS_PROFILE_MAPPING)
    public final ProfileResponse createUsersProfile(@RequestBody ProfileRequest profileRequest) {
        return profileService.createUsersProfile(profileRequest);
    }

    @Override
    @GetMapping(value = ProfileMappings.GET_BASIC_PROFILE_INFO_MAPPING)
    public final ProfileResponse getBasicProfileInfo(@RequestParam String username) {
        return profileService.getBasicProfileInfo(username);
    }

}
