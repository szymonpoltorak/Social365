import { Profile } from "@interfaces/feed/profile.interface";

export interface Comment {
    commentLikesCount: number;
    content: string;
    author: Profile;
    hasReplies: boolean;
    isLiked: boolean;
}
