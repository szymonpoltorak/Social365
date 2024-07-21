import { CommentKey } from "@interfaces/posts-comments/comment-key.interface";

export interface CommentEditRequest {
    profileId: string;
    content: string;
    hasAttachment: boolean;
    commentKey: CommentKey;
}
