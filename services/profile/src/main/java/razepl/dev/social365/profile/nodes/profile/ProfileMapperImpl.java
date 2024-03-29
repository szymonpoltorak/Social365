package razepl.dev.social365.profile.nodes.profile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
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
import razepl.dev.social365.profile.clients.images.ImagesServiceClient;
import razepl.dev.social365.profile.clients.images.data.ImageResponse;
import razepl.dev.social365.profile.nodes.about.details.AboutDetails;
import razepl.dev.social365.profile.nodes.about.mapper.AboutMapper;
import razepl.dev.social365.profile.nodes.about.workplace.Workplace;
import razepl.dev.social365.profile.nodes.profile.interfaces.ProfileMapper;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProfileMapperImpl implements ProfileMapper {

    private static final String PROFILE_IS_NULL = "Profile is null";
    private final ImagesServiceClient imagesServiceClient;
    private final AboutMapper aboutMapper;

    @Override
    public final ProfileSummaryResponse mapProfileToProfileSummaryResponse(Profile profile) {
        if (profile == null) {
            log.info(PROFILE_IS_NULL);

            return null;
        }
        return ProfileSummaryResponse
                .builder()
                .username(profile.getEmail().getEmailValue())
                .followersCount(profile.getFollowers().size())
                .friendsCount(profile.getFriends().size())
                .postsCount(0) //TODO: Implement posts count
                .profilePictureUrl(getProfilePicturePath(profile))
                .subtitle(getSubtitle(profile))
                .bio(profile.getBio())
                .fullName(profile.getFullName())
                .profileId(profile.getProfileId())
                .build();
    }

    @Override
    public final ProfilePostResponse mapProfileToProfilePostResponse(Profile profile) {
        if (profile == null) {
            log.info(PROFILE_IS_NULL);

            return null;
        }
        return ProfilePostResponse
                .builder()
                .username(profile.getEmail().getEmailValue())
                .subtitle(getSubtitle(profile))
                .fullName(profile.getFullName())
                .profilePictureUrl(getProfilePicturePath(profile))
                .build();
    }

    @Override
    public final ProfileResponse mapProfileToProfileResponse(Profile profile) {
        if (profile == null) {
            log.info(PROFILE_IS_NULL);

            return null;
        }
        return ProfileResponse
                .builder()
                .bio(profile.getBio())
                .fullName(profile.getFullName())
                .profilePictureLink(getProfilePicturePath(profile))
                .username(profile.getEmail().getEmailValue())
                .build();
    }

    @Override
    public final ProfileRequest mapProfileToProfileRequest(Profile profile) {
        if (profile == null) {
            log.info(PROFILE_IS_NULL);

            return null;
        }
        return ProfileRequest
                .builder()
                .username(profile.getEmail().getEmailValue())
                .dateOfBirth(profile.getBirthDate().getDateOfBirth())
                .lastName(profile.getLastName())
                .name(profile.getFirstName())
                .build();
    }

    @Override
    public final OverviewResponse mapProfileToOverviewResponse(Profile profile) {
        if (profile == null) {
            log.info(PROFILE_IS_NULL);

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
            log.info(PROFILE_IS_NULL);

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
            log.info(PROFILE_IS_NULL);

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
            log.info(PROFILE_IS_NULL);

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
            log.info(PROFILE_IS_NULL);

            return null;
        }
        return FriendResponse
                .builder()
                .fullName(profile.getFullName())
                .profilePictureUrl(getProfilePicturePath(profile))
                .profileId(profile.getProfileId())
                .numOfMutualFriends(numOfMutualFriends)
                .isFollowed(isFollowed)
                .build();
    }

    @Override
    public final FriendSuggestionResponse mapProfileToFriendSuggestionResponse(Profile profile,
                                                                               int numOfMutualFriends) {
        if (profile == null) {
            log.info(PROFILE_IS_NULL);

            return null;
        }
        return FriendSuggestionResponse
                .builder()
                .fullName(profile.getFullName())
                .profileId(profile.getProfileId())
                .numOfMutualFriends(numOfMutualFriends)
                .profilePictureUrl(getProfilePicturePath(profile))
                .build();
    }

    private String getProfilePicturePath(Profile profile) {
        ImageResponse profilePicture = imagesServiceClient.getImagePath(profile.getProfilePictureId());

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
        return null;
    }
}
