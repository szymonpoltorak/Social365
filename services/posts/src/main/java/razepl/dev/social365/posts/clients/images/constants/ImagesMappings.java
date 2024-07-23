package razepl.dev.social365.posts.clients.images.constants;

public final class ImagesMappings {

    private static final String IMAGES_MAPPING = "/api/v1/images";

    public static final String GET_POST_IMAGES_MAPPING = IMAGES_MAPPING + "/getPostImages";

    public static final String GET_COMMENT_IMAGE_MAPPING = IMAGES_MAPPING + "/getCommentImage";

    public static final String DELETE_IMAGES_BY_POST_ID_MAPPING = IMAGES_MAPPING + "/deleteImagesByPostId";

    public static final String DELETE_COMMENT_IMAGE_BY_ID_MAPPING = IMAGES_MAPPING + "/deleteCommentImageById";

    private ImagesMappings() {
    }
}
