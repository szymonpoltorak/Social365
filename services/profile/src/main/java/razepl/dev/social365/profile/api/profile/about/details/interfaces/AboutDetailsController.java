package razepl.dev.social365.profile.api.profile.about.details.interfaces;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import razepl.dev.social365.profile.api.profile.about.experience.data.AboutDetailsRequest;
import razepl.dev.social365.profile.api.profile.about.experience.data.DateOfBirthRequest;
import razepl.dev.social365.profile.api.profile.about.experience.data.GenderRequest;
import razepl.dev.social365.profile.api.profile.about.experience.data.RelationshipStatusRequest;
import razepl.dev.social365.profile.api.profile.data.ProfileRequest;
import razepl.dev.social365.profile.config.User;

@Tag(name = "AboutDetailsController", description = "Operations pertaining to about details in Social365")
public interface AboutDetailsController {

    @Operation(summary = "Update profile gender")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated profile gender"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    ProfileRequest updateProfileGender(
            @Parameter(description = "Authenticated user") User user,
            @Parameter(description = "Gender request") GenderRequest genderRequest
    );

    @Operation(summary = "Delete profile gender")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted profile gender"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    ProfileRequest deleteProfileGender(
            @Parameter(description = "Authenticated user") User user
    );

    @Operation(summary = "Update profile date of birth")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated profile date of birth"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    ProfileRequest updateProfileDateOfBirth(
            @Parameter(description = "Authenticated user") User user,
            @Parameter(description = "Date of birth request") DateOfBirthRequest dateOfBirthRequest
    );

    @Operation(summary = "Update profile relationship status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated profile relationship status"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    ProfileRequest updateProfileRelationshipStatus(
            @Parameter(description = "Authenticated user") User user,
            @Parameter(description = "Relationship status request") RelationshipStatusRequest relationshipStatusRequest
    );

    @Operation(summary = "Delete profile relationship status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted profile relationship status"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    ProfileRequest deleteProfileRelationshipStatus(
            @Parameter(description = "Authenticated user") User user
    );

    @Operation(summary = "Delete profile current city")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted profile current city"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    ProfileRequest deleteProfileCurrentCity(
            @Parameter(description = "Authenticated user") User user
    );

    @Operation(summary = "Delete profile home town")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted profile home town"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    ProfileRequest deleteProfileHomeTown(
            @Parameter(description = "Authenticated user") User user
    );

    @Operation(summary = "Update profile current city")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated profile current city"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    ProfileRequest updateProfileCurrentCity(
            @Parameter(description = "Authenticated user") User user,
            @Parameter(description = "City request") AboutDetailsRequest cityRequest
    );

    @Operation(summary = "Update profile home town")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated profile home town"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    ProfileRequest updateProfileHomeTown(
            @Parameter(description = "Authenticated user") User user,
            @Parameter(description = "City request") AboutDetailsRequest cityRequest
    );

}
