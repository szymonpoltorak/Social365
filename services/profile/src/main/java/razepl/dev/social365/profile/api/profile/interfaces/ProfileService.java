package razepl.dev.social365.profile.api.profile.interfaces;

import org.springframework.data.domain.Page;
import razepl.dev.social365.profile.api.profile.data.BirthdayInfoResponse;
import razepl.dev.social365.profile.api.profile.data.ProfilePostResponse;
import razepl.dev.social365.profile.api.profile.data.ProfileRequest;
import razepl.dev.social365.profile.api.profile.data.ProfileResponse;
import razepl.dev.social365.profile.api.profile.data.ProfileSummaryResponse;

public interface ProfileService {

    Page<BirthdayInfoResponse> getTodayBirthdays(String profileId, int page);

    ProfileResponse getBasicProfileInfoByUsername(String username);

    ProfileSummaryResponse getProfileSummary(String userId);

    ProfilePostResponse getPostProfileInfo(String userId);

    ProfileResponse createUsersProfile(ProfileRequest profileRequest);

    ProfileResponse getBasicProfileInfo(String userId);

    ProfileRequest updateProfileBio(String profileId, String bio);

}
