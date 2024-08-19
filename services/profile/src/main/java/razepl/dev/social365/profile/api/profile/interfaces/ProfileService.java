package razepl.dev.social365.profile.api.profile.interfaces;

import org.springframework.data.domain.Pageable;
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

public interface ProfileService {

    SocialPage<BirthdayInfoResponse> getTodayBirthdays(User user, int page);

    SocialPage<ProfileSearchResponse> getProfilesSearchByPattern(String pattern, Pageable pageable);

    SocialPage<ProfileQueryResponse> getProfilesByPattern(String pattern, Pageable pageable);

    ProfileBasicResponse getBasicProfileInfoByUsername(String username, String currentUserId);

    ProfileSummaryResponse getProfileSummary(User user);

    ProfilePostResponse getPostProfileInfo(String userId);

    ProfileResponse createUsersProfile(ProfileRequest profileRequest);

    ProfileResponse getBasicProfileInfo(User user);

    ProfileResponse getProfileInfoByUsername(String username);

    ProfileRequest updateProfileBio(User user, String bio);

    ProfileRequest updateProfilePicture(User user, long profilePictureId);

    ProfileRequest updateProfileBanner(User user, long profileBannerId);

}
