package razepl.dev.social365.profile.api.profile.about.experience.interfaces;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import razepl.dev.social365.profile.api.profile.about.experience.data.AboutDetailsRequest;
import razepl.dev.social365.profile.api.profile.about.experience.data.WorkPlaceRequest;
import razepl.dev.social365.profile.api.profile.data.ProfileRequest;
import razepl.dev.social365.profile.config.User;

@Tag(name = "AboutExperienceController", description = "Operations pertaining to user experience in Social365")
public interface AboutExperienceController {

    @Operation(summary = "Update profile work place")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated profile work place"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    ProfileRequest updateProfileWorkPlace(
            @Parameter(description = "Authenticated user") User user,
            @Parameter(description = "Work place request") WorkPlaceRequest workPlaceRequest
    );

    @Operation(summary = "Update profile college")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated profile college"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    ProfileRequest updateProfileCollege(
            @Parameter(description = "Authenticated user") User user,
            @Parameter(description = "Education request") AboutDetailsRequest educationRequest
    );

    @Operation(summary = "Update profile high school")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated profile high school"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    ProfileRequest updateProfileHighSchool(
            @Parameter(description = "Authenticated user") User user,
            @Parameter(description = "High school request") AboutDetailsRequest highSchoolRequest
    );

    @Operation(summary = "Delete profile high school")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted profile high school"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    ProfileRequest deleteProfileHighSchool(
            @Parameter(description = "Authenticated user") User user
    );

    @Operation(summary = "Delete profile work place")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted profile work place"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    ProfileRequest deleteProfileWorkPlace(
            @Parameter(description = "Authenticated user") User user
    );

    @Operation(summary = "Delete profile college")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted profile college"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    ProfileRequest deleteProfileCollege(
            @Parameter(description = "Authenticated user") User user
    );

}
