import { User } from "@core/data/feed/User";

export interface PostComment {
    commentId: number;
    commentLikesCount: number;
    content: string;
    author: User;
    creationDateTime: Date;
    isLiked: boolean;
}
