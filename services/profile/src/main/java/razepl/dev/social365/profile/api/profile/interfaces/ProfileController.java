package razepl.dev.social365.profile.api.profile.interfaces;

import razepl.dev.social365.profile.api.profile.data.BirthdayInfoResponse;
import razepl.dev.social365.profile.api.profile.data.ProfileBasicResponse;
import razepl.dev.social365.profile.api.profile.data.ProfilePostResponse;
import razepl.dev.social365.profile.api.profile.data.ProfileQueryResponse;
import razepl.dev.social365.profile.api.profile.data.ProfileRequest;
import razepl.dev.social365.profile.api.profile.data.ProfileResponse;
import razepl.dev.social365.profile.api.profile.data.ProfileSearchResponse;
import razepl.dev.social365.profile.api.profile.data.ProfileSummaryResponse;
import razepl.dev.social365.profile.config.User;
import razepl.dev.social365.profile.utils.pagination.SocialPage;

public interface ProfileController {

    SocialPage<BirthdayInfoResponse> getTodayBirthdays(User user, int pageNumber);

    SocialPage<ProfileSearchResponse> getProfilesSearchByPattern(String pattern, int pageNumber, int pageSize);

    SocialPage<ProfileQueryResponse> getProfilesByPattern(String pattern, int pageNumber, int pageSize);

    ProfileBasicResponse getBasicProfileInfoByUsername(String username, User user);

    ProfileSummaryResponse getProfileSummary(User user);

    ProfilePostResponse getPostProfileInfo(String profileId);

    ProfileResponse createUsersProfile(ProfileRequest profileRequest);

    ProfileResponse getBasicProfileInfo(User user);

    ProfileResponse getProfileInfoByUsername(String username);

    ProfileRequest updateProfileBio(User user, String bio);

    ProfileRequest updateProfilePicture(User user, long profilePictureId);

    ProfileRequest updateProfileBanner(User user, long profileBannerId);

}
