package razepl.dev.social365.profile.api.profile.about.overview.interfaces;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import razepl.dev.social365.profile.api.profile.about.overview.data.ContactInfoResponse;
import razepl.dev.social365.profile.api.profile.about.overview.data.LocationsResponse;
import razepl.dev.social365.profile.api.profile.about.overview.data.OverviewResponse;
import razepl.dev.social365.profile.api.profile.about.overview.data.WorkEducationResponse;

@Tag(name = "AboutDataController", description = "Operations pertaining to user's about data in Social365")
public interface AboutDataController {

    @Operation(summary = "Get overview by username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved overview"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    OverviewResponse getOverview(
            @Parameter(description = "Username") String username
    );

    @Operation(summary = "Get work and education by username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved work and education"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    WorkEducationResponse getWorkEducation(
            @Parameter(description = "Username") String username
    );

    @Operation(summary = "Get locations by username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved locations"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    LocationsResponse getLocations(
            @Parameter(description = "Username") String username
    );

    @Operation(summary = "Get contact info by username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved contact info"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    ContactInfoResponse getContactInfo(
            @Parameter(description = "Username") String username
    );

}
