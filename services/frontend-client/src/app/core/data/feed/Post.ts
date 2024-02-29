export interface Post {
    areNotificationTurnedOn: boolean;
    isBookmarked: boolean;
    authorFullName: string;
    creationDate: Date;
    content: string;
    likes: number;
    comments: number;
    shares: number;
    imageLink: string;
    isPostLiked: boolean;
}