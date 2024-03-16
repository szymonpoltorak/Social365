import { User } from "@interfaces/feed/user.interface";

export interface PostComment {
    commentId: number;
    commentLikesCount: number;
    content: string;
    author: User;
    creationDateTime: Date;
    isLiked: boolean;
}
