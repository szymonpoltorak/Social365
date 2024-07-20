import { Profile } from "@interfaces/feed/profile.interface";

export interface Comment {
    commentLikesCount: number;
    content: string;
    author: Profile;
    imageUrl: string;
    hasReplies: boolean;
    isLiked: boolean;
}
