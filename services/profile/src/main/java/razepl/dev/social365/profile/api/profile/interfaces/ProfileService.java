package razepl.dev.social365.profile.api.profile.interfaces;

import razepl.dev.social365.profile.api.profile.data.ProfilePostResponse;
import razepl.dev.social365.profile.api.profile.data.ProfileRequest;
import razepl.dev.social365.profile.api.profile.data.ProfileResponse;
import razepl.dev.social365.profile.api.profile.data.ProfileSummaryResponse;

import java.util.List;

public interface ProfileService {

    ProfileSummaryResponse getProfileSummary(String userId);

    List<String> getFollowedProfileIds(String profileId);

    ProfilePostResponse getPostProfileInfo(String userId);

    ProfileResponse createUsersProfile(ProfileRequest profileRequest);

    ProfileResponse getBasicProfileInfo(String userId);

    ProfileRequest updateProfileBio(String profileId, String bio);

}
