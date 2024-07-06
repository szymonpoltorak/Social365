export interface CommentAddRequest {
    postId: string;
    profileId: string;
    content: string;
    hasAttachments: boolean;
}
