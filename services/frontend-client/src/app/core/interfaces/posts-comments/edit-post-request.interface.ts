export interface EditPostRequest {
    profileId: string;
    postId: string;
    creationDateTime: string;
    content: string;
    hasAttachments: boolean;
}
