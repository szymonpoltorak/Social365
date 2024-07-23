export enum CommentMappings {

    COMMENT_MAPPING = "/api/v1/posts/comments",

    GET_COMMENTS_FOR_POST = COMMENT_MAPPING + "/getCommentsForPost",

    ADD_COMMENT_TO_POST = COMMENT_MAPPING + "/addCommentToPost",

    EDIT_COMMENT = COMMENT_MAPPING + "/editComment",

    DELETE_COMMENT = COMMENT_MAPPING + "/deleteComment",

    UPDATE_LIKE_COMMENT_COUNT = COMMENT_MAPPING + "/updateLikeCommentCount"

}
