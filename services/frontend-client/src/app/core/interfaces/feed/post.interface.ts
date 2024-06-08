import { Profile } from "@interfaces/feed/profile.interface";
import { PostStatistics } from "@interfaces/feed/post-statistics.interface";

export interface Post {
    postId: number;
    areNotificationTurnedOn: boolean;
    isBookmarked: boolean;
    creationDateTime: Date;
    content: string;
    statistics: PostStatistics;
    isPostLiked: boolean;
    author: Profile;
    imageLinks: string[];
}
