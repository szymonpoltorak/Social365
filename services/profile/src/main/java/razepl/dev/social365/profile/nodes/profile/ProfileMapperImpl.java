package razepl.dev.social365.profile.nodes.profile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import razepl.dev.social365.profile.api.friends.data.FriendData;
import razepl.dev.social365.profile.api.friends.data.FriendFeedResponse;
import razepl.dev.social365.profile.api.friends.data.FriendProfile;
import razepl.dev.social365.profile.api.friends.data.FriendResponse;
import razepl.dev.social365.profile.api.friends.data.FriendSuggestion;
import razepl.dev.social365.profile.api.friends.data.FriendSuggestionResponse;
import razepl.dev.social365.profile.api.profile.about.overview.data.ContactInfoResponse;
import razepl.dev.social365.profile.api.profile.about.overview.data.LocationsResponse;
import razepl.dev.social365.profile.api.profile.about.overview.data.OverviewResponse;
import razepl.dev.social365.profile.api.profile.about.overview.data.WorkEducationResponse;
import razepl.dev.social365.profile.api.profile.data.BirthdayData;
import razepl.dev.social365.profile.api.profile.data.BirthdayInfoResponse;
import razepl.dev.social365.profile.api.profile.data.ProfileBasicResponse;
import razepl.dev.social365.profile.api.profile.data.ProfilePostResponse;
import razepl.dev.social365.profile.api.profile.data.ProfileQueryResponse;
import razepl.dev.social365.profile.api.profile.data.ProfileRequest;
import razepl.dev.social365.profile.api.profile.data.ProfileResponse;
import razepl.dev.social365.profile.api.profile.data.ProfileSearchResponse;
import razepl.dev.social365.profile.api.profile.data.ProfileSummaryResponse;
import razepl.dev.social365.profile.clients.images.ImagesServiceClient;
import razepl.dev.social365.profile.clients.images.data.ImageResponse;
import razepl.dev.social365.profile.clients.posts.comments.PostCommentsService;
import razepl.dev.social365.profile.utils.exceptions.ProfileNotFoundException;
import razepl.dev.social365.profile.nodes.about.details.AboutDetails;
import razepl.dev.social365.profile.nodes.about.mapper.AboutMapper;
import razepl.dev.social365.profile.nodes.about.workplace.Workplace;
import razepl.dev.social365.profile.nodes.profile.interfaces.ProfileMapper;
import razepl.dev.social365.profile.nodes.profile.interfaces.ProfileRepository;

import java.time.LocalDate;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProfileMapperImpl implements ProfileMapper {

    private static final String DEFAULT_PROFILE_IMAGE_PATH = "/images/nouser@example.com/shiba1.jpg";

    private final ImagesServiceClient imagesServiceClient;
    private final AboutMapper aboutMapper;
    private final PostCommentsService postCommentsService;
    private final ProfileRepository profileRepository;

    @Override
    public final ProfileSummaryResponse mapProfileToProfileSummaryResponse(Profile profile) {
        if (profile == null) {
            return null;
        }
        return ProfileSummaryResponse
                .builder()
                .username(profile.getEmail().getEmailValue())
                .followersCount(profileRepository.getFollowersCount(profile.getProfileId()))
                .friendsCount(profileRepository.getFriendsCount(profile.getProfileId()))
                .postsCount(postCommentsService.getUsersPostCount(profile.getProfileId()))
                .profilePictureUrl(getProfilePicturePath(profile.getProfilePictureId()))
                .profileBannerUrl(getProfilePicturePath(profile.getBannerPictureId()))
                .subtitle(getSubtitle(profile))
                .bio(profile.getBio())
                .fullName(profile.getFullName())
                .profileId(profile.getProfileId())
                .build();
    }

    @Override
    public final ProfilePostResponse mapProfileToProfilePostResponse(Profile profile) {
        if (profile == null) {
            return null;
        }
        return ProfilePostResponse
                .builder()
                .profileId(profile.getProfileId())
                .username(profile.getEmail().getEmailValue())
                .subtitle(getSubtitle(profile))
                .fullName(profile.getFullName())
                .profilePictureUrl(getProfilePicturePath(profile.getProfilePictureId()))
                .build();
    }

    @Override
    public final ProfileResponse mapProfileToProfileResponse(Profile profile) {
        if (profile == null) {
            return null;
        }
        return ProfileResponse
                .builder()
                .profileId(profile.getProfileId())
                .bio(profile.getBio())
                .fullName(profile.getFullName())
                .profilePictureUrl(getProfilePicturePath(profile.getProfilePictureId()))
                .profileBannerUrl(getProfilePicturePath(profile.getBannerPictureId()))
                .username(profile.getEmail().getEmailValue())
                .subtitle(getSubtitle(profile))
                .build();
    }

    @Override
    public final ProfileRequest mapProfileToProfileRequest(Profile profile) {
        if (profile == null) {
            return null;
        }
        return ProfileRequest
                .builder()
                .username(profile.getEmail().getEmailValue())
                .dateOfBirth(profile.getBirthDate().getDateOfBirth())
                .lastName(profile.getLastName())
                .firstName(profile.getFirstName())
                .build();
    }

    @Override
    public final OverviewResponse mapProfileToOverviewResponse(Profile profile) {
        if (profile == null) {
            return null;
        }
        return OverviewResponse
                .builder()
                .relationshipStatus(aboutMapper.mapRelationshipStatusToResponse(profile.getRelationshipStatus()))
                .workplace(aboutMapper.mapWorkplaceToAboutOptionResponse(profile.getWorkplace()))
                .college(aboutMapper.mapAboutDetailsToAboutOptionResponse(profile.getCollege()))
                .highSchool(aboutMapper.mapAboutDetailsToAboutOptionResponse(profile.getHighSchool()))
                .homeTown(aboutMapper.mapAboutDetailsToAboutOptionResponse(profile.getHomeTown()))
                .build();
    }

    @Override
    public final LocationsResponse mapProfileToLocationsResponse(Profile profile) {
        if (profile == null) {
            return null;
        }
        return LocationsResponse
                .builder()
                .currentCity(aboutMapper.mapAboutDetailsToAboutOptionResponse(profile.getCurrentCity()))
                .homeTown(aboutMapper.mapAboutDetailsToAboutOptionResponse(profile.getHomeTown()))
                .build();
    }

    @Override
    public final WorkEducationResponse mapProfileToWorkEducationResponse(Profile profile) {
        if (profile == null) {
            return null;
        }
        return WorkEducationResponse
                .builder()
                .workplace(aboutMapper.mapWorkplaceToAboutOptionResponse(profile.getWorkplace()))
                .college(aboutMapper.mapAboutDetailsToAboutOptionResponse(profile.getCollege()))
                .highSchool(aboutMapper.mapAboutDetailsToAboutOptionResponse(profile.getHighSchool()))
                .build();
    }

    @Override
    public final ContactInfoResponse mapProfileToContactInfoResponse(Profile profile) {
        if (profile == null) {
            return null;
        }
        return ContactInfoResponse
                .builder()
                .mobile(aboutMapper.mapMobileNumberToMobileNumberResponse(profile.getPhoneNumber()))
                .email(aboutMapper.mapEmailToAboutOptionResponse(profile.getEmail()))
                .gender(aboutMapper.mapGenderToAboutOptionResponse(profile.getGender()))
                .birthDate(aboutMapper.mapBirthdayToAboutOptionResponse(profile.getBirthDate()))
                .build();
    }

    @Override
    public final FriendResponse mapProfileToFriendResponse(Profile profile, int numOfMutualFriends,
                                                           boolean isFollowed) {
        if (profile == null) {
            return null;
        }
        return FriendResponse
                .builder()
                .fullName(profile.getFullName())
                .profilePictureUrl(getProfilePicturePath(profile.getProfilePictureId()))
                .profileId(profile.getProfileId())
                .numOfMutualFriends(numOfMutualFriends)
                .username(profile.getEmail().getEmailValue())
                .isFollowed(isFollowed)
                .build();
    }

    @Override
    public final FriendSuggestionResponse mapProfileToFriendSuggestionResponse(Profile profile,
                                                                               int numOfMutualFriends) {
        if (profile == null) {
            return null;
        }
        return FriendSuggestionResponse
                .builder()
                .fullName(profile.getFullName())
                .profileId(profile.getProfileId())
                .numOfMutualFriends(numOfMutualFriends)
                .profilePictureUrl(getProfilePicturePath(profile.getProfilePictureId()))
                .build();
    }

    @Override
    public final FriendSuggestionResponse mapFriendSuggestionToFriendSuggestionResponse(FriendSuggestion friendSuggestion) {
        return mapProfileToFriendSuggestionResponse(friendSuggestion.profile(), friendSuggestion.mutualFriendsCount());
    }

    @Override
    public final FriendResponse mapFriendDataToFriendResponse(FriendData friendData) {
        FriendProfile profile = friendData.profile();

        log.info("Friend profile: {}", profile);

        return FriendResponse
                .builder()
                .fullName(profile.getFullName())
                .profilePictureUrl(getProfilePicturePath(profile.profilePictureId()))
                .profileId(profile.profileId())
                .numOfMutualFriends(friendData.mutualFriendsCount())
                .username(profile.email())
                .isFollowed(friendData.isFollowed())
                .build();
    }

    @Override
    public final BirthdayInfoResponse mapBirthdayDataToBirthdayInfoResponse(BirthdayData birthdayData) {
        Profile friend = profileRepository.findByProfileId(birthdayData.friendId())
                .orElseThrow(ProfileNotFoundException::new);

        return BirthdayInfoResponse
                .builder()
                .profile(mapProfileToProfileResponse(friend))
                .age(birthdayData.birthDate().getAge())
                .build();
    }

    @Override
    public final FriendFeedResponse mapProfileToFriendFeedResponse(Profile profile) {
        return FriendFeedResponse
                .builder()
                .isOnline(profile.isOnline())
                .profileId(profile.getProfileId())
                .profilePictureUrl(getProfilePicturePath(profile.getProfilePictureId()))
                .fullName(profile.getFullName())
                .username(profile.getEmail().getEmailValue())
                .build();
    }

    @Override
    public final ProfileQueryResponse mapProfileToProfileQueryResponse(Profile profile) {
        return ProfileQueryResponse
                .builder()
                .profileId(profile.getProfileId())
                .profilePictureUrl(getProfilePicturePath(profile.getProfilePictureId()))
                .fullName(profile.getFullName())
                .build();
    }

    @Override
    public final ProfileSearchResponse mapProfileToProfileSearchResponse(Profile profile) {
        return ProfileSearchResponse
                .builder()
                .profileId(profile.getProfileId())
                .profilePictureUrl(getProfilePicturePath(profile.getProfilePictureId()))
                .fullName(profile.getFullName())
                .subtitle(getSubtitle(profile))
                .username(profile.getEmail().getEmailValue())
                .build();
    }

    @Override
    public final ProfileBasicResponse mapProfileToProfileBasicResponse(Profile profile, String currentUserId) {
        if (profile == null) {
            return null;
        }
        return ProfileBasicResponse
                .builder()
                .profileId(profile.getProfileId())
                .profilePictureUrl(getProfilePicturePath(profile.getProfilePictureId()))
                .profileBannerUrl(getProfilePicturePath(profile.getBannerPictureId()))
                .bio(profile.getBio())
                .subtitle(getSubtitle(profile))
                .fullName(profile.getFullName())
                .username(profile.getEmail().getEmailValue())
                .isFriend(profileRepository.areUsersFriends(currentUserId, profile.getProfileId()))
                .isFollowing(profileRepository.doesUserFollowProfile(currentUserId, profile.getProfileId()))
                .build();
    }

    private String getProfilePicturePath(long pictureId) {
        if (pictureId == -1L) {
            return "";
        }
        if (pictureId == 0L) {
            return DEFAULT_PROFILE_IMAGE_PATH;
        }
        ImageResponse profilePicture = imagesServiceClient.getImagePath(pictureId);

        log.info("Profile picture response: {}", profilePicture);

        return profilePicture.imagePath();
    }

    private String getSubtitle(Profile profile) {
        Workplace workplace = profile.getWorkplace();

        if (workplace != null) {
            return workplace.getSubtitle();
        }
        AboutDetails college = profile.getCollege();

        if (college != null) {
            return college.getPropertyValue();
        }
        AboutDetails highSchool = profile.getHighSchool();

        if (highSchool != null) {
            return highSchool.getPropertyValue();
        }
        return profile.getEmail().getEmailValue();
    }
}
