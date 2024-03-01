export interface Post {
    postId: number;
    areNotificationTurnedOn: boolean;
    isBookmarked: boolean;
    authorFullName: string;
    postAuthorIconLink: string;
    creationDateTime: Date;
    content: string;
    likes: number;
    comments: number;
    shares: number;
    imageLink: string;
    isPostLiked: boolean;
}