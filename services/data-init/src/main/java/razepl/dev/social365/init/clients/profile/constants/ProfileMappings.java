package razepl.dev.social365.init.clients.profile.constants;

public final class ProfileMappings {
    private static final String PROFILE_API_MAPPING = "/api/v1/profile";

    public static final String ADD_FRIEND = PROFILE_API_MAPPING + "/friends/addFriend";

    public static final String GET_PROFILE_SUMMARY_MAPPING = PROFILE_API_MAPPING + "/summary";

    public static final String GET_POST_PROFILE_INFO_MAPPING = PROFILE_API_MAPPING + "/postInfo";

    public static final String CREATE_USERS_PROFILE_MAPPING = PROFILE_API_MAPPING + "/create";

    public static final String GET_BASIC_PROFILE_INFO_MAPPING = PROFILE_API_MAPPING + "/basicInfo";

    public static final String UPDATE_PROFILE_BIO_MAPPING = PROFILE_API_MAPPING + "/updateBio";

    public static final String GET_FOLLOWED_IDS = PROFILE_API_MAPPING + "/getFollowedIds";

    private ProfileMappings() {
    }
}
