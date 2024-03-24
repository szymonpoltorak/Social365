package razepl.dev.social365.profile.nodes.profile;

import org.springframework.stereotype.Component;
import razepl.dev.social365.profile.api.profile.data.ProfilePostResponse;
import razepl.dev.social365.profile.api.profile.data.ProfileResponse;
import razepl.dev.social365.profile.api.profile.data.ProfileSummaryResponse;
import razepl.dev.social365.profile.nodes.about.details.AboutDetails;
import razepl.dev.social365.profile.nodes.about.workplace.Workplace;
import razepl.dev.social365.profile.nodes.profile.interfaces.ProfileMapper;

@Component
public class ProfileMapperImpl implements ProfileMapper {

    @Override
    public final ProfileSummaryResponse mapProfileToProfileSummaryResponse(Profile profile) {
        if (profile == null) {
            return null;
        }
        return ProfileSummaryResponse
                .builder()
                .username(profile.getUsername())
                .followersCount(profile.getFollowers().size())
                .friendsCount(profile.getFriends().size())
                .postsCount(0) //TODO: Implement posts count
                .profilePictureUrl("") //TODO: Implement profile picture url
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
                .username(profile.getUsername())
                .subtitle(getSubtitle(profile))
                .fullName(profile.getFullName())
                .profilePictureUrl("") //TODO: Implement profile picture url
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
                .profilePictureLink("") //TODO: Implement profile picture url
                .username(profile.getUsername())
                .build();
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
