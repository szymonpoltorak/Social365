package razepl.dev.social365.profile.api.profile.about.overview.interfaces;

import razepl.dev.social365.profile.api.profile.about.overview.data.ContactInfoResponse;
import razepl.dev.social365.profile.api.profile.about.overview.data.LocationsResponse;
import razepl.dev.social365.profile.api.profile.about.overview.data.OverviewResponse;
import razepl.dev.social365.profile.api.profile.about.overview.data.WorkEducationResponse;

public interface AboutDataController {

    OverviewResponse getOverview(String profileId);

    WorkEducationResponse getWorkEducation(String profileId);

    LocationsResponse getLocations(String profileId);

    ContactInfoResponse getContactInfo(String profileId);

}
