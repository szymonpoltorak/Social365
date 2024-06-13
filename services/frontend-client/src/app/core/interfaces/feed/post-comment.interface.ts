import { Profile } from "@interfaces/feed/profile.interface";

export interface PostComment {
    commentId: string;
    commentLikesCount: number;
    content: string;
    author: Profile;
    creationDateTime: Date;
    isLiked: boolean;
}
