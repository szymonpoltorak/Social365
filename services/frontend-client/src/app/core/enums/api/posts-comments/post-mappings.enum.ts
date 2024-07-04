export enum PostMappings {
    POST_MAPPING = "/api/v1/posts",

    GET_POSTS_ON_PAGE = POST_MAPPING + "/getPostsOnPage",

    UPDATE_LIKE_POST_COUNT = POST_MAPPING + "/updateLikePostCount",

    UPDATE_NOTIFICATION_STATUS = POST_MAPPING + "/updateNotificationStatus",

    UPDATE_BOOKMARK_STATUS = POST_MAPPING + "/updateBookmarkStatus",

    SHARE_POST = POST_MAPPING + "/updateSharesCount",

    CREATE_POST = POST_MAPPING + "/createPost",

    EDIT_POST = POST_MAPPING + "/editPost",

    DELETE_POST = POST_MAPPING + "/deletePost",

    GET_USERS_POST_COUNT = POST_MAPPING + "/usersPostCount",

    GET_USERS_POSTS = POST_MAPPING + "/usersPosts"
}
