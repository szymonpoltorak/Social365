import { CommentKey } from "@interfaces/posts-comments/comment-key.interface";

export interface CommentEditRequest {
    content: string;
    hasAttachment: boolean;
    commentKey: CommentKey;
}
