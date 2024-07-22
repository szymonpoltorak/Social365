package razepl.dev.social365.profile.api.profile.interfaces;

import org.springframework.data.domain.Page;
import razepl.dev.social365.profile.api.profile.data.BirthdayInfoResponse;
import razepl.dev.social365.profile.api.profile.data.ProfilePostResponse;
import razepl.dev.social365.profile.api.profile.data.ProfileQueryResponse;
import razepl.dev.social365.profile.api.profile.data.ProfileRequest;
import razepl.dev.social365.profile.api.profile.data.ProfileResponse;
import razepl.dev.social365.profile.api.profile.data.ProfileSearchResponse;
import razepl.dev.social365.profile.api.profile.data.ProfileSummaryResponse;

public interface ProfileController {

    Page<BirthdayInfoResponse> getTodayBirthdays(String profileId, int pageNumber);

    Page<ProfileSearchResponse> getProfilesSearchByPattern(String pattern, int pageNumber, int pageSize);

    Page<ProfileQueryResponse> getProfilesByPattern(String pattern, int pageNumber, int pageSize);

    ProfileResponse getBasicProfileInfoByUsername(String username);

    ProfileSummaryResponse getProfileSummary(String profileId);

    ProfilePostResponse getPostProfileInfo(String profileId);

    ProfileResponse createUsersProfile(ProfileRequest profileRequest);

    ProfileResponse getBasicProfileInfo(String profileId);

    ProfileRequest updateProfileBio(String profileId, String bio);

    ProfileRequest updateProfilePicture(String profileId, long profilePictureId);

    ProfileRequest updateProfileBanner(String profileId, long profileBannerId);

}
