import { CommentKey } from "@interfaces/posts-comments/comment-key.interface";
import { Comment } from "@interfaces/posts-comments/comment.interface";

export interface PostComment extends Comment{
    commentKey: CommentKey;
}
