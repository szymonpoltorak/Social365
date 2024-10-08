export enum ImagesMappings {

    IMAGES_MAPPING = "/api/v1/images",

    UPLOAD_IMAGE_MAPPING = IMAGES_MAPPING + "/upload",

    GET_IMAGE_MAPPING = IMAGES_MAPPING + "/getImage",

    DELETE_IMAGE_MAPPING = IMAGES_MAPPING + "/deleteImage",

    UPDATE_IMAGE_MAPPING = IMAGES_MAPPING + "/updateImage",

    UPLOAD_POST_IMAGE_MAPPING = IMAGES_MAPPING + "/uploadPostImage",

    UPLOAD_COMMENT_IMAGE_MAPPING = IMAGES_MAPPING + "/uploadCommentImage",

    GET_POST_IMAGES_MAPPING = IMAGES_MAPPING + "/getPostImages",

    GET_COMMENT_IMAGE_MAPPING = IMAGES_MAPPING + "/getCommentImage",

    GET_USER_UPLOADED_IMAGES_MAPPING = IMAGES_MAPPING + "/getUserUploadedImages",

    DELETE_IMAGE_BY_URL_MAPPING = IMAGES_MAPPING + "/deleteImageByUrl",

    DELETE_POST_IMAGE_BY_URL_MAPPING = "/deletePostImageByUrl",

    DELETE_COMMENT_IMAGE_BY_ID_MAPPING = "/deleteCommentImageById",

}
