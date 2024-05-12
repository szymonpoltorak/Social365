import { User } from "@interfaces/feed/user.interface";

export interface PostComment {
    commentId: string;
    commentLikesCount: number;
    content: string;
    author: User;
    creationDateTime: Date;
    isLiked: boolean;
}
