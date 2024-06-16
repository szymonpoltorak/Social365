package razepl.dev.social365.profile.clients.posts.comments.constants;

public final class PostCommentsMappings {

    private static final String POSTS_MAPPING = "/api/v1/posts";
    public static final String CREATE_POST = POSTS_MAPPING + "/createPost";
    public static final String GET_USERS_POST_COUNT = POSTS_MAPPING + "/usersPostCount";
    private static final String POSTS_COMMENTS_MAPPING = "/api/v1/posts/comments";
    public static final String ADD_COMMENT_TO_POST = POSTS_COMMENTS_MAPPING + "/addCommentToPost";

    private PostCommentsMappings() {
    }
}
