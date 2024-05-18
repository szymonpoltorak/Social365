package razepl.dev.social365.profile.api.profile.interfaces;

import org.springframework.data.domain.Page;
import razepl.dev.social365.profile.api.profile.data.ProfilePostResponse;
import razepl.dev.social365.profile.api.profile.data.ProfileRequest;
import razepl.dev.social365.profile.api.profile.data.ProfileResponse;
import razepl.dev.social365.profile.api.profile.data.ProfileSummaryResponse;

public interface ProfileController {

    ProfileSummaryResponse getProfileSummary(String profileId);

    Page<String> getFollowedProfileIds(String profileId, int pageNumber);

    ProfilePostResponse getPostProfileInfo(String profileId);

    ProfileResponse createUsersProfile(ProfileRequest profileRequest);

    ProfileResponse getBasicProfileInfo(String profileId);

    ProfileRequest updateProfileBio(String profileId, String bio);

}
