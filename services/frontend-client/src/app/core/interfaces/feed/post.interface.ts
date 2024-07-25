import { PostStatistics } from "@interfaces/feed/post-statistics.interface";
import { PostKey } from "@interfaces/feed/post-key.interface";

export interface Post {
    postKey: PostKey;
    areNotificationTurnedOn: boolean;
    isBookmarked: boolean;
    content: string;
    statistics: PostStatistics;
    isPostLiked: boolean;
    imageUrls: string[];
}
