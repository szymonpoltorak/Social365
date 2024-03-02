import { Author } from "@core/data/feed/Author";

export interface PostComment {
    commentId: number;
    commentLikesCount: number;
    content: string;
    author: Author;
    creationDateTime: Date;
    isLiked: boolean;
}
