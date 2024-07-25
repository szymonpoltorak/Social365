export interface EditPostRequest {
    authorId: string;
    postId: string;
    content: string;
    hasAttachments: boolean;
}
