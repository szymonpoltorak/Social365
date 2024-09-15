package razepl.dev.social365.profile.api.friends.interfaces;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import razepl.dev.social365.profile.api.friends.data.FriendFeedResponse;
import razepl.dev.social365.profile.api.friends.data.FriendResponse;
import razepl.dev.social365.profile.api.friends.data.FriendSuggestionResponse;
import razepl.dev.social365.profile.config.auth.User;
import razepl.dev.social365.profile.utils.pagination.SocialPage;

@Tag(name = "FriendsController", description = "Operations pertaining to friends in Social365")
public interface FriendsController {

    @Operation(summary = "Get friends on a specific page")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved friends"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    SocialPage<FriendResponse> getFriends(
            @Parameter(description = "Authenticated user") User user,
            @Parameter(description = "Page number") int pageNumber,
            @Parameter(description = "Page size") int pageSize
    );

    @Operation(summary = "Search friends by pattern")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved friends"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    SocialPage<FriendResponse> getFriendsByPattern(
            @Parameter(description = "Authenticated user") User user,
            @Parameter(description = "Search pattern") String pattern,
            @Parameter(description = "Page number") int pageNumber,
            @Parameter(description = "Page size") int pageSize
    );

    @Operation(summary = "Get friends feed options")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved feed options"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    SocialPage<FriendFeedResponse> getFriendsFeedOptions(
            @Parameter(description = "Profile ID") String profileId,
            @Parameter(description = "Page number") int pageNumber,
            @Parameter(description = "Page size") int pageSize
    );

    @Operation(summary = "Get followed profile IDs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved followed profile IDs"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    SocialPage<String> getFollowedProfileIds(
            @Parameter(description = "Authenticated user") User user,
            @Parameter(description = "Page number") int pageNumber
    );

    @Operation(summary = "Get friend requests")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved friend requests"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    SocialPage<FriendSuggestionResponse> getFriendRequests(
            @Parameter(description = "Authenticated user") User user,
            @Parameter(description = "Page number") int pageNumber,
            @Parameter(description = "Page size") int pageSize
    );

    @Operation(summary = "Get friend suggestions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved friend suggestions"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    SocialPage<FriendSuggestionResponse> getFriendSuggestions(
            @Parameter(description = "Authenticated user") User user,
            @Parameter(description = "Page number") int pageNumber,
            @Parameter(description = "Page size") int pageSize
    );

    @Operation(summary = "Remove user from friends")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully removed user from friends"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    FriendResponse removeUserFromFriends(
            @Parameter(description = "Authenticated user") User user,
            @Parameter(description = "Friend ID") String friendId
    );

    @Operation(summary = "Add user to friends")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully added user to friends"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    FriendResponse addUserToFriends(
            @Parameter(description = "Authenticated user") User user,
            @Parameter(description = "Friend ID") String friendId
    );

    @Operation(summary = "Add profile to followed")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully added profile to followed"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    FriendResponse addProfileToFollowed(
            @Parameter(description = "Authenticated user") User user,
            @Parameter(description = "Profile ID to follow") String friendId
    );

    @Operation(summary = "Remove profile from followed")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully removed profile from followed"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    FriendResponse removeProfileFromFollowed(
            @Parameter(description = "Authenticated user") User user,
            @Parameter(description = "Profile ID to unfollow") String toFollowId
    );

    @Operation(summary = "Send friend request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully sent friend request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    FriendResponse sendFriendRequest(
            @Parameter(description = "Authenticated user") User user,
            @Parameter(description = "Friend ID") String friendId
    );

    @Operation(summary = "Accept friend request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully accepted friend request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    FriendResponse acceptFriendRequest(
            @Parameter(description = "Authenticated user") User user,
            @Parameter(description = "Friend ID") String friendId
    );

    @Operation(summary = "Decline friend request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully declined friend request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    FriendResponse declineFriendRequest(
            @Parameter(description = "Authenticated user") User user,
            @Parameter(description = "Friend ID") String friendId
    );

}
