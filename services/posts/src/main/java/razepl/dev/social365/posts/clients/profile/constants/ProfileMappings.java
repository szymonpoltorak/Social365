package razepl.dev.social365.posts.clients.profile.constants;

public final class ProfileMappings {

    private static final String PROFILE_MAPPING = "/api/v1/profile";

    public static final String GET_FOLLOWED_IDS = PROFILE_MAPPING + "/friends/getFollowedIds";

    public static final String GET_POST_PROFILE_INFO_MAPPING = PROFILE_MAPPING + "/postInfo";

    private ProfileMappings() {
    }

}
