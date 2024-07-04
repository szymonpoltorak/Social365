import { Profile } from "@interfaces/feed/profile.interface";
import { PostStatistics } from "@interfaces/feed/post-statistics.interface";

export interface Post {
    postId: string;
    areNotificationTurnedOn: boolean;
    isBookmarked: boolean;
    creationDateTime: string;
    content: string;
    statistics: PostStatistics;
    isPostLiked: boolean;
    author: Profile;
    imageUrls: string[];
}
