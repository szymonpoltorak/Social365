package razepl.dev.social365.profile.nodes.profile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import razepl.dev.social365.profile.api.friends.data.FriendData;
import razepl.dev.social365.profile.api.friends.data.FriendResponse;
import razepl.dev.social365.profile.api.friends.data.FriendSuggestion;
import razepl.dev.social365.profile.api.friends.data.FriendSuggestionResponse;
import razepl.dev.social365.profile.api.profile.about.overview.data.ContactInfoResponse;
import razepl.dev.social365.profile.api.profile.about.overview.data.LocationsResponse;
import razepl.dev.social365.profile.api.profile.about.overview.data.OverviewResponse;
import razepl.dev.social365.profile.api.profile.about.overview.data.WorkEducationResponse;
import razepl.dev.social365.profile.api.profile.data.BirthdayData;
import razepl.dev.social365.profile.api.profile.data.BirthdayInfoResponse;
import razepl.dev.social365.profile.api.profile.data.ProfilePostResponse;
import razepl.dev.social365.profile.api.profile.data.ProfileRequest;
import razepl.dev.social365.profile.api.profile.data.ProfileResponse;
import razepl.dev.social365.profile.api.profile.data.ProfileSummaryResponse;
import razepl.dev.social365.profile.clients.images.ImagesServiceClient;
import razepl.dev.social365.profile.clients.images.data.ImageResponse;
import razepl.dev.social365.profile.clients.posts.comments.PostCommentsService;
import razepl.dev.social365.profile.exceptions.ProfileDetailsNotFoundException;
import razepl.dev.social365.profile.nodes.about.birthdate.BirthDate;
import razepl.dev.social365.profile.nodes.about.birthdate.BirthDateRepository;
import razepl.dev.social365.profile.nodes.about.details.AboutDetails;
import razepl.dev.social365.profile.nodes.about.details.enums.DetailsType;
import razepl.dev.social365.profile.nodes.about.mail.Email;
import razepl.dev.social365.profile.nodes.about.mail.interfaces.EmailRepository;
import razepl.dev.social365.profile.nodes.about.mapper.AboutMapper;
import razepl.dev.social365.profile.nodes.about.workplace.Workplace;
import razepl.dev.social365.profile.nodes.profile.interfaces.ProfileMapper;
import razepl.dev.social365.profile.nodes.profile.interfaces.ProfileRepository;

import java.time.LocalDate;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProfileMapperImpl implements ProfileMapper {

    private static final String PROFILE_IS_NULL = "Profile is null";
    private final ImagesServiceClient imagesServiceClient;
    private final AboutMapper aboutMapper;
    private final PostCommentsService postCommentsService;
    private final ProfileRepository profileRepository;
    private final EmailRepository emailRepository;
    private final BirthDateRepository birthDateRepository;

    @Override
    public final ProfileSummaryResponse mapProfileToProfileSummaryResponse(Profile profile) {
        if (profile == null) {
            log.info(PROFILE_IS_NULL);

            return null;
        }
        Email email = emailRepository.findByProfileId(profile.getProfileId())
                .orElseThrow(ProfileDetailsNotFoundException::new);

        return ProfileSummaryResponse
                .builder()
                .username(email.getEmailValue())
                .followersCount(profileRepository.getFollowersCount(profile.getProfileId()))
                .friendsCount(profileRepository.getFriendsCount(profile.getProfileId()))
                .postsCount(postCommentsService.getUsersPostCount(profile.getProfileId()))
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
        Email email = emailRepository.findByProfileId(profile.getProfileId())
                .orElseThrow(ProfileDetailsNotFoundException::new);

        return ProfilePostResponse
                .builder()
                .profileId(profile.getProfileId())
                .username(email.getEmailValue())
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
        Email email = emailRepository.findByProfileId(profile.getProfileId())
                .orElseThrow(ProfileDetailsNotFoundException::new);

        return ProfileResponse
                .builder()
                .profileId(profile.getProfileId())
                .bio(profile.getBio())
                .fullName(profile.getFullName())
                .profilePictureUrl(getProfilePicturePath(profile))
                .username(email.getEmailValue())
                .build();
    }

    @Override
    public final ProfileRequest mapProfileToProfileRequest(Profile profile) {
        if (profile == null) {
            log.info(PROFILE_IS_NULL);

            return null;
        }
        Email email = emailRepository.findByProfileId(profile.getProfileId())
                .orElseThrow(ProfileDetailsNotFoundException::new);
        BirthDate birthDate = birthDateRepository.findByProfileId(profile.getProfileId())
                .orElseThrow(ProfileDetailsNotFoundException::new);

        return ProfileRequest
                .builder()
                .username(email.getEmailValue())
                .dateOfBirth(LocalDate.parse(birthDate.getDateOfBirth()))
                .lastName(profile.getLastName())
                .firstName(profile.getFirstName())
                .build();
    }

    @Override
    public final OverviewResponse mapProfileToOverviewResponse(Profile profile) {
        if (profile == null) {
            log.info(PROFILE_IS_NULL);

            return null;
        }
        String profileId = profile.getProfileId();

        return OverviewResponse
                .builder()
                .relationshipStatus(aboutMapper.mapRelationshipStatusToResponse(profileId))
                .workplace(aboutMapper.mapWorkplaceToAboutOptionResponse(profileId))
                .college(aboutMapper.mapAboutDetailsToAboutOptionResponse(profileId, DetailsType.COLLEGE))
                .highSchool(aboutMapper.mapAboutDetailsToAboutOptionResponse(profileId, DetailsType.HIGH_SCHOOL))
                .homeTown(aboutMapper.mapAboutDetailsToAboutOptionResponse(profileId, DetailsType.HOMETOWN))
                .build();
    }

    @Override
    public final LocationsResponse mapProfileToLocationsResponse(Profile profile) {
        if (profile == null) {
            log.info(PROFILE_IS_NULL);

            return null;
        }
        String profileId = profile.getProfileId();

        return LocationsResponse
                .builder()
                .currentCity(aboutMapper.mapAboutDetailsToAboutOptionResponse(profileId, DetailsType.CURRENT_CITY))
                .homeTown(aboutMapper.mapAboutDetailsToAboutOptionResponse(profileId, DetailsType.HOMETOWN))
                .build();
    }

    @Override
    public final WorkEducationResponse mapProfileToWorkEducationResponse(Profile profile) {
        if (profile == null) {
            log.info(PROFILE_IS_NULL);

            return null;
        }
        String profileId = profile.getProfileId();

        return WorkEducationResponse
                .builder()
                .workplace(aboutMapper.mapWorkplaceToAboutOptionResponse(profileId))
                .college(aboutMapper.mapAboutDetailsToAboutOptionResponse(profileId, DetailsType.COLLEGE))
                .highSchool(aboutMapper.mapAboutDetailsToAboutOptionResponse(profileId, DetailsType.HIGH_SCHOOL))
                .build();
    }

    @Override
    public final ContactInfoResponse mapProfileToContactInfoResponse(Profile profile) {
        if (profile == null) {
            log.info(PROFILE_IS_NULL);

            return null;
        }
        String profileId = profile.getProfileId();

        return ContactInfoResponse
                .builder()
                .mobile(aboutMapper.mapMobileNumberToMobileNumberResponse(profileId))
                .email(aboutMapper.mapEmailToAboutOptionResponse(profileId))
                .gender(aboutMapper.mapGenderToAboutOptionResponse(profileId))
                .birthDate(aboutMapper.mapBirthdayToAboutOptionResponse(profileId))
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

    @Override
    public final FriendSuggestionResponse mapFriendSuggestionToFriendSuggestionResponse(FriendSuggestion friendSuggestion) {
        return mapProfileToFriendSuggestionResponse(friendSuggestion.profile(), friendSuggestion.mutualFriendsCount());
    }

    @Override
    public final FriendResponse mapFriendDataToFriendResponse(FriendData friendData) {
        return mapProfileToFriendResponse(
                friendData.profile(),
                friendData.mutualFriendsCount(),
                friendData.isFollowed()
        );
    }

    @Override
    public final BirthdayInfoResponse mapBirthdayDataToBirthdayInfoResponse(BirthdayData birthdayData) {
        return BirthdayInfoResponse
                .builder()
                .profile(mapProfileToProfileResponse(birthdayData.friend()))
                .age(birthdayData.birthDate().getAge())
                .build();
    }

    private String getProfilePicturePath(Profile profile) {
        ImageResponse profilePicture = imagesServiceClient.getImagePath(profile.getProfilePictureId());

        log.info("Profile picture response: {}", profilePicture);

        return profilePicture.imagePath();
    }

    private String getSubtitle(Profile profile) {
        Workplace workplace = profileRepository.getWorkplaceByProfileId(profile.getProfileId());

        if (workplace != null) {
            return workplace.getSubtitle();
        }
        AboutDetails college = profileRepository.getCollegeByProfileId(profile.getProfileId());

        if (college != null) {
            return college.getPropertyValue();
        }
        AboutDetails highSchool = profileRepository.getHighSchoolByProfileId(profile.getProfileId());

        if (highSchool != null) {
            return highSchool.getPropertyValue();
        }
        return null;
    }
}
