package razepl.dev.social365.profile.api.profile.about.contact.interfaces;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import razepl.dev.social365.profile.api.profile.about.experience.data.AboutDetailsRequest;
import razepl.dev.social365.profile.api.profile.data.ProfileRequest;
import razepl.dev.social365.profile.config.User;
import razepl.dev.social365.profile.nodes.enums.PrivacyLevel;

@Tag(name = "AboutContactController", description = "Operations pertaining to contact details in Social365")
public interface AboutContactController {

    @Operation(summary = "Update profile phone number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated profile phone number"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    ProfileRequest updateProfilePhoneNumber(
            @Parameter(description = "Authenticated user") User user,
            @Parameter(description = "Phone number request") AboutDetailsRequest phoneNumberRequest
    );

    @Operation(summary = "Update profile email privacy level")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated profile email privacy level"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    ProfileRequest updateProfileEmailPrivacyLevel(
            @Parameter(description = "Authenticated user") User user,
            @Parameter(description = "Privacy level") PrivacyLevel privacyLevel
    );

    @Operation(summary = "Delete profile phone number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted profile phone number"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    ProfileRequest deleteProfilePhoneNumber(
            @Parameter(description = "Authenticated user") User user
    );

}
