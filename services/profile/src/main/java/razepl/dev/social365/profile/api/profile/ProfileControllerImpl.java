package razepl.dev.social365.profile.api.profile;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
import razepl.dev.social365.profile.api.profile.data.ProfileBasicResponse;
import razepl.dev.social365.profile.api.profile.data.ProfilePostResponse;
import razepl.dev.social365.profile.api.profile.data.ProfileQueryResponse;
import razepl.dev.social365.profile.api.profile.data.ProfileRequest;
import razepl.dev.social365.profile.api.profile.data.ProfileResponse;
import razepl.dev.social365.profile.api.profile.data.ProfileSearchResponse;
import razepl.dev.social365.profile.api.profile.data.ProfileSummaryResponse;
import razepl.dev.social365.profile.api.profile.interfaces.ProfileController;
import razepl.dev.social365.profile.api.profile.interfaces.ProfileService;
import razepl.dev.social365.profile.config.AuthUser;
import razepl.dev.social365.profile.config.User;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = ProfileMappings.PROFILE_API_MAPPING)
public class ProfileControllerImpl implements ProfileController {

    private final ProfileService profileService;

    @Override
    @GetMapping(value = ProfileMappings.GET_TODAY_BIRTHDAYS_MAPPING)
    public final Page<BirthdayInfoResponse> getTodayBirthdays(@AuthUser User user,
                                                              @RequestParam(Params.PAGE_NUMBER) int pageNumber) {
        return profileService.getTodayBirthdays(user, pageNumber);
    }

    @Override
    @GetMapping(value = ProfileMappings.GET_PROFILES_SEARCH_BY_PATTERN_MAPPING)
    public final Page<ProfileSearchResponse> getProfilesSearchByPattern(@RequestParam(Params.PATTERN) String pattern,
                                                                        @RequestParam(Params.PAGE_NUMBER) int pageNumber,
                                                                        @RequestParam(Params.PAGE_SIZE) int pageSize) {
        return profileService.getProfilesSearchByPattern(pattern, PageRequest.of(pageNumber, pageSize));
    }

    @Override
    @GetMapping(value = ProfileMappings.GET_PROFILES_BY_PATTERN_MAPPING)
    public final Page<ProfileQueryResponse> getProfilesByPattern(@RequestParam(Params.PATTERN) String pattern,
                                                                 @RequestParam(Params.PAGE_NUMBER) int pageNumber,
                                                                 @RequestParam(Params.PAGE_SIZE) int pageSize) {
        return profileService.getProfilesByPattern(pattern, PageRequest.of(pageNumber, pageSize));
    }

    @Override
    @GetMapping(value = ProfileMappings.GET_BASIC_PROFILE_INFO_BY_USERNAME_MAPPING)
    public final ProfileBasicResponse getBasicProfileInfoByUsername(@RequestParam(Params.USERNAME) String username,
                                                                    @AuthUser User user) {
        return profileService.getBasicProfileInfoByUsername(username, user.profileId());
    }

    @Override
    @GetMapping(value = ProfileMappings.GET_PROFILE_SUMMARY_MAPPING)
    public final ProfileSummaryResponse getProfileSummary(@AuthUser User user) {
        return profileService.getProfileSummary(user);
    }

    @Override
    @PutMapping(value = ProfileMappings.UPDATE_PROFILE_BIO_MAPPING)
    public final ProfileRequest updateProfileBio(@AuthUser User user,
                                                 @RequestParam(Params.BIO) String bio) {
        return profileService.updateProfileBio(user, bio);
    }

    @Override
    @PutMapping(value = ProfileMappings.UPDATE_PROFILE_PICTURE_MAPPING)
    public final ProfileRequest updateProfilePicture(@AuthUser User user,
                                                     @RequestParam(Params.PROFILE_PICTURE_ID) long profilePictureId) {
        return profileService.updateProfilePicture(user, profilePictureId);
    }

    @Override
    @PutMapping(value = ProfileMappings.UPDATE_PROFILE_BANNER_MAPPING)
    public final ProfileRequest updateProfileBanner(@AuthUser User user,
                                                    @RequestParam(Params.PROFILE_BANNER_ID) long profileBannerId) {
        return profileService.updateProfileBanner(user, profileBannerId);
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
    public final ProfileResponse getBasicProfileInfo(@AuthUser User user) {
        return profileService.getBasicProfileInfo(user);
    }

    @Override
    @GetMapping(value = ProfileMappings.GET_PROFILE_INFO_BY_USERNAME_MAPPING)
    public final ProfileResponse getProfileInfoByUsername(@RequestParam(Params.USERNAME) String username) {
        return profileService.getProfileInfoByUsername(username);
    }

}
