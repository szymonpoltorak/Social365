import { Author } from "@core/data/feed/Author";

export interface Post {
    postId: number;
    areNotificationTurnedOn: boolean;
    isBookmarked: boolean;
    creationDateTime: Date;
    content: string;
    likes: number;
    comments: number;
    shares: number;
    imageLink: string;
    isPostLiked: boolean;
    author: Author;
}
