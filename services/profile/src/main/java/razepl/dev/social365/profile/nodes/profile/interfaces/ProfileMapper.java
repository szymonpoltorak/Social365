package razepl.dev.social365.profile.nodes.profile.interfaces;

import razepl.dev.social365.profile.api.profile.data.ProfilePostResponse;
import razepl.dev.social365.profile.api.profile.data.ProfileRequest;
import razepl.dev.social365.profile.api.profile.data.ProfileResponse;
import razepl.dev.social365.profile.api.profile.data.ProfileSummaryResponse;
import razepl.dev.social365.profile.nodes.profile.Profile;

public interface ProfileMapper {

    ProfileSummaryResponse mapProfileToProfileSummaryResponse(Profile profile);

    ProfilePostResponse mapProfileToProfilePostResponse(Profile profile);

    ProfileResponse mapProfileToProfileResponse(Profile profile);

    ProfileRequest mapProfileToProfileRequest(Profile profile);
}
