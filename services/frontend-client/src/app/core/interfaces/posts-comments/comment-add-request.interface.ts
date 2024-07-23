export interface CommentAddRequest {
    postId: string;
    profileId: string;
    content: string;
    hasAttachment: boolean;
}
