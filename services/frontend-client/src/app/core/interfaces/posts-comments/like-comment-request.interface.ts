import { CommentKey } from "@interfaces/posts-comments/comment-key.interface";

export interface LikeCommentRequest {
    commentKey: CommentKey;
    profileId: string;
}
