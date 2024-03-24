package razepl.dev.social365.profile.nodes.profile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import razepl.dev.social365.profile.api.profile.data.ProfilePostResponse;
import razepl.dev.social365.profile.api.profile.data.ProfileRequest;
import razepl.dev.social365.profile.api.profile.data.ProfileResponse;
import razepl.dev.social365.profile.api.profile.data.ProfileSummaryResponse;
import razepl.dev.social365.profile.clients.images.ImagesServiceClient;
import razepl.dev.social365.profile.clients.images.data.ImageResponse;
import razepl.dev.social365.profile.nodes.about.details.AboutDetails;
import razepl.dev.social365.profile.nodes.about.workplace.Workplace;
import razepl.dev.social365.profile.nodes.profile.interfaces.ProfileMapper;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProfileMapperImpl implements ProfileMapper {

    private final ImagesServiceClient imagesServiceClient;

    @Override
    public final ProfileSummaryResponse mapProfileToProfileSummaryResponse(Profile profile) {
        if (profile == null) {
            return null;
        }
        return ProfileSummaryResponse
                .builder()
                .username(profile.getMail().getEmail())
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
            return null;
        }
        return ProfilePostResponse
                .builder()
                .username(profile.getMail().getEmail())
                .subtitle(getSubtitle(profile))
                .fullName(profile.getFullName())
                .profilePictureUrl(getProfilePicturePath(profile))
                .build();
    }

    @Override
    public final ProfileResponse mapProfileToProfileResponse(Profile profile) {
        if (profile == null) {
            return null;
        }
        return ProfileResponse
                .builder()
                .bio(profile.getBio())
                .fullName(profile.getFullName())
                .profilePictureLink(getProfilePicturePath(profile))
                .username(profile.getMail().getEmail())
                .build();
    }

    @Override
    public final ProfileRequest mapProfileToProfileRequest(Profile profile) {
        if (profile == null) {
            return null;
        }
        return ProfileRequest
                .builder()
                .username(profile.getMail().getEmail())
                .dateOfBirth(profile.getBirthDate().getDateOfBirth())
                .lastName(profile.getLastName())
                .name(profile.getFirstName())
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
