package razepl.dev.social365.profile.nodes.profile.interfaces;

import razepl.dev.social365.profile.api.friends.data.FriendResponse;
import razepl.dev.social365.profile.api.friends.data.FriendSuggestionResponse;
import razepl.dev.social365.profile.api.profile.about.overview.data.ContactInfoResponse;
import razepl.dev.social365.profile.api.profile.about.overview.data.LocationsResponse;
import razepl.dev.social365.profile.api.profile.about.overview.data.OverviewResponse;
import razepl.dev.social365.profile.api.profile.about.overview.data.WorkEducationResponse;
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

    OverviewResponse mapProfileToOverviewResponse(Profile profile);

    LocationsResponse mapProfileToLocationsResponse(Profile profile);

    WorkEducationResponse mapProfileToWorkEducationResponse(Profile profile);

    ContactInfoResponse mapProfileToContactInfoResponse(Profile profile);

    FriendResponse mapProfileToFriendResponse(Profile profile, int numOfMutualFriends, boolean isFollowed);

    FriendSuggestionResponse mapProfileToFriendSuggestionResponse(Profile profile, int numOfMutualFriends);
}
