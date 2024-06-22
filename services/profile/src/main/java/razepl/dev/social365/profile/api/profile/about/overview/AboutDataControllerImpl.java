package razepl.dev.social365.profile.api.profile.about.overview;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import razepl.dev.social365.profile.api.profile.about.overview.constants.AboutMappings;
import razepl.dev.social365.profile.api.profile.about.overview.data.ContactInfoResponse;
import razepl.dev.social365.profile.api.profile.about.overview.data.LocationsResponse;
import razepl.dev.social365.profile.api.profile.about.overview.data.OverviewResponse;
import razepl.dev.social365.profile.api.profile.about.overview.data.WorkEducationResponse;
import razepl.dev.social365.profile.api.profile.about.overview.interfaces.AboutDataController;
import razepl.dev.social365.profile.api.profile.about.overview.interfaces.AboutDataService;
import razepl.dev.social365.profile.api.profile.constants.Params;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = AboutMappings.ABOUT_MAPPING)
public class AboutDataControllerImpl implements AboutDataController {

    private final AboutDataService aboutDataService;

    @Override
    @GetMapping(value = AboutMappings.OVERVIEW_MAPPING)
    public final OverviewResponse getOverview(@RequestParam(Params.USERNAME) String username) {
        return aboutDataService.getOverview(username);
    }

    @Override
    @GetMapping(value = AboutMappings.WORK_EDUCATION_MAPPING)
    public final WorkEducationResponse getWorkEducation(@RequestParam(Params.USERNAME) String username) {
        return aboutDataService.getWorkEducation(username);
    }

    @Override
    @GetMapping(value = AboutMappings.LOCATIONS_MAPPING)
    public final LocationsResponse getLocations(@RequestParam(Params.USERNAME) String username) {
        return aboutDataService.getLocations(username);
    }

    @Override
    @GetMapping(value = AboutMappings.CONTACT_INFO_MAPPING)
    public final ContactInfoResponse getContactInfo(@RequestParam(Params.USERNAME) String username) {
        return aboutDataService.getContactInfo(username);
    }
}
