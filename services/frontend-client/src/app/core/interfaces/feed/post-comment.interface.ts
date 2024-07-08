import { Profile } from "@interfaces/feed/profile.interface";
import { CommentKey } from "@interfaces/posts-comments/comment-key.interface";

export interface PostComment {
    commentKey: CommentKey;
    commentLikesCount: number;
    content: string;
    author: Profile;
    hasReplies: boolean;
    isLiked: boolean;
}
