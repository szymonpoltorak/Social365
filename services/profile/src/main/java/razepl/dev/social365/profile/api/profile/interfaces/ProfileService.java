package razepl.dev.social365.profile.api.profile.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import razepl.dev.social365.profile.api.profile.data.BirthdayInfoResponse;
import razepl.dev.social365.profile.api.profile.data.ProfileBasicResponse;
import razepl.dev.social365.profile.api.profile.data.ProfilePostResponse;
import razepl.dev.social365.profile.api.profile.data.ProfileQueryResponse;
import razepl.dev.social365.profile.api.profile.data.ProfileRequest;
import razepl.dev.social365.profile.api.profile.data.ProfileResponse;
import razepl.dev.social365.profile.api.profile.data.ProfileSearchResponse;
import razepl.dev.social365.profile.api.profile.data.ProfileSummaryResponse;

public interface ProfileService {

    Page<BirthdayInfoResponse> getTodayBirthdays(String profileId, int page);

    Page<ProfileSearchResponse> getProfilesSearchByPattern(String pattern, Pageable pageable);

    Page<ProfileQueryResponse> getProfilesByPattern(String pattern, Pageable pageable);

    ProfileBasicResponse getBasicProfileInfoByUsername(String username, String currentUserId);

    ProfileSummaryResponse getProfileSummary(String userId);

    ProfilePostResponse getPostProfileInfo(String userId);

    ProfileResponse createUsersProfile(ProfileRequest profileRequest);

    ProfileResponse getBasicProfileInfo(String userId);

    ProfileResponse getProfileInfoByUsername(String username);

    ProfileRequest updateProfileBio(String profileId, String bio);

    ProfileRequest updateProfilePicture(String profileId, long profilePictureId);

    ProfileRequest updateProfileBanner(String profileId, long profileBannerId);

}
