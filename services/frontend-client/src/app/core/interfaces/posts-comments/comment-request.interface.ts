import { CommentKey } from "@interfaces/posts-comments/comment-key.interface";

export interface CommentRequest {
    profileId: string;
    content: string;
    hasAttachments: boolean;
    commentKey: CommentKey;
}
