import { CommentKey } from "@interfaces/posts-comments/comment-key.interface";

export interface CommentDeleteRequest {
    commentKey: CommentKey;
    profileId: string;
}
