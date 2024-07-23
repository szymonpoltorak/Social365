import { ReplyKey } from "@interfaces/posts-comments/reply-key.interface";
import { Comment } from "@interfaces/posts-comments/comment.interface";

export interface ReplyComment extends Comment {
    commentKey: ReplyKey;
}
