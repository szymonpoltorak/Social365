package razepl.dev.social365.profile.api.profile.interfaces;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import razepl.dev.social365.profile.api.profile.data.BirthdayInfoResponse;
import razepl.dev.social365.profile.api.profile.data.ProfileBasicResponse;
import razepl.dev.social365.profile.api.profile.data.ProfilePostResponse;
import razepl.dev.social365.profile.api.profile.data.ProfileQueryResponse;
import razepl.dev.social365.profile.api.profile.data.ProfileRequest;
import razepl.dev.social365.profile.api.profile.data.ProfileResponse;
import razepl.dev.social365.profile.api.profile.data.ProfileSearchResponse;
import razepl.dev.social365.profile.api.profile.data.ProfileSummaryResponse;
import razepl.dev.social365.profile.config.User;
import razepl.dev.social365.profile.utils.pagination.SocialPage;

@Tag(name = "ProfileController", description = "Operations pertaining to profiles in Social365")
public interface ProfileController {

    @Operation(summary = "Get today's birthdays")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved today's birthdays"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    SocialPage<BirthdayInfoResponse> getTodayBirthdays(
            @Parameter(description = "Authenticated user") User user,
            @Parameter(description = "Page number") int pageNumber
    );

    @Operation(summary = "Search profiles by pattern")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved profiles"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    SocialPage<ProfileSearchResponse> getProfilesSearchByPattern(
            @Parameter(description = "Search pattern") String pattern,
            @Parameter(description = "Page number") int pageNumber,
            @Parameter(description = "Page size") int pageSize
    );

    @Operation(summary = "Get profiles by pattern")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved profiles"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    SocialPage<ProfileQueryResponse> getProfilesByPattern(
            @Parameter(description = "Search pattern") String pattern,
            @Parameter(description = "Page number") int pageNumber,
            @Parameter(description = "Page size") int pageSize
    );

    @Operation(summary = "Get basic profile info by username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved profile info"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    ProfileBasicResponse getBasicProfileInfoByUsername(
            @Parameter(description = "Username") String username,
            @Parameter(description = "Authenticated user") User user
    );

    @Operation(summary = "Get profile summary")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved profile summary"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    ProfileSummaryResponse getProfileSummary(
            @Parameter(description = "Authenticated user") User user
    );

    @Operation(summary = "Get post profile info")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved post profile info"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    ProfilePostResponse getPostProfileInfo(
            @Parameter(description = "Profile ID") String profileId
    );

    @Operation(summary = "Create user's profile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created profile"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    ProfileResponse createUsersProfile(
            @Parameter(description = "Profile request") ProfileRequest profileRequest
    );

    @Operation(summary = "Get basic profile info")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved profile info"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    ProfileResponse getBasicProfileInfo(
            @Parameter(description = "Authenticated user") User user
    );

    @Operation(summary = "Get profile info by username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved profile info"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    ProfileResponse getProfileInfoByUsername(
            @Parameter(description = "Username") String username
    );

    @Operation(summary = "Update profile bio")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated profile bio"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    ProfileRequest updateProfileBio(
            @Parameter(description = "Authenticated user") User user,
            @Parameter(description = "Bio") String bio
    );

    @Operation(summary = "Update profile picture")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated profile picture"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    ProfileRequest updateProfilePicture(
            @Parameter(description = "Authenticated user") User user,
            @Parameter(description = "Profile picture ID") long profilePictureId
    );

    @Operation(summary = "Update profile banner")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated profile banner"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    ProfileRequest updateProfileBanner(
            @Parameter(description = "Authenticated user") User user,
            @Parameter(description = "Profile banner ID") long profileBannerId
    );

}
