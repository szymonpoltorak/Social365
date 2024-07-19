export enum RepliesMappings {

    REPLIES_MAPPING = "/api/v1/comments/replies",

    GET_REPLIES_FOR_COMMENT = REPLIES_MAPPING + "/get",

    ADD_REPLY_TO_COMMENT = REPLIES_MAPPING + "/add",

    EDIT_REPLY = REPLIES_MAPPING + "/edit",

    DELETE_REPLY = REPLIES_MAPPING + "/delete",
    
    UPDATE_LIKE_REPLY_COUNT = REPLIES_MAPPING + "/updateLikeCount",

}
