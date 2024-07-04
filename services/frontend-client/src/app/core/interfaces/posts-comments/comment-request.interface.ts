export interface CommentRequest {
    objectId: string;
    profileId: string;
    content: string;
    replyToCommentId: string;
    hasAttachments: boolean;
}
