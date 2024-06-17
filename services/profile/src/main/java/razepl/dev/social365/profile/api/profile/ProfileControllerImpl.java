package razepl.dev.social365.profile.api.profile;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import razepl.dev.social365.profile.api.profile.constants.Params;
import razepl.dev.social365.profile.api.profile.constants.ProfileMappings;
import razepl.dev.social365.profile.api.profile.data.BirthdayInfoResponse;
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
    @GetMapping(value = ProfileMappings.GET_TODAY_BIRTHDAYS_MAPPING)
    public final Page<BirthdayInfoResponse> getTodayBirthdays(@Param(Params.PROFILE_ID) String profileId,
                                                              @Param(Params.PAGE_NUMBER) int pageNumber) {
        return profileService.getTodayBirthdays(profileId, pageNumber);
    }

    @Override
    @GetMapping(value = ProfileMappings.GET_BASIC_PROFILE_INFO_BY_USERNAME_MAPPING)
    public final ProfileResponse getBasicProfileInfoByUsername(@RequestParam(Params.USERNAME) String username) {
        return profileService.getBasicProfileInfoByUsername(username);
    }

    @Override
    @GetMapping(value = ProfileMappings.GET_PROFILE_SUMMARY_MAPPING)
    public final ProfileSummaryResponse getProfileSummary(@RequestParam(Params.PROFILE_ID) String profileId) {
        return profileService.getProfileSummary(profileId);
    }

    @Override
    @PutMapping(value = ProfileMappings.UPDATE_PROFILE_BIO_MAPPING)
    public final ProfileRequest updateProfileBio(@RequestParam(Params.PROFILE_ID) String profileId,
                                                 @RequestParam(Params.BIO) String bio) {
        return profileService.updateProfileBio(profileId, bio);
    }

    @Override
    @GetMapping(value = ProfileMappings.GET_POST_PROFILE_INFO_MAPPING)
    public final ProfilePostResponse getPostProfileInfo(@RequestParam(Params.PROFILE_ID) String profileId) {
        return profileService.getPostProfileInfo(profileId);
    }

    @Override
    @PostMapping(value = ProfileMappings.CREATE_USERS_PROFILE_MAPPING)
    public final ProfileResponse createUsersProfile(@RequestBody ProfileRequest profileRequest) {
        return profileService.createUsersProfile(profileRequest);
    }

    @Override
    @GetMapping(value = ProfileMappings.GET_BASIC_PROFILE_INFO_MAPPING)
    public final ProfileResponse getBasicProfileInfo(@RequestParam(Params.PROFILE_ID) String profileId) {
        return profileService.getBasicProfileInfo(profileId);
    }

}
