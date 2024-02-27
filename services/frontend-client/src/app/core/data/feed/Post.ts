export interface Post {
    authorFullName: string;
    creationDate: Date;
    content: string;
    likes: number;
    comments: number;
    shares: number;
    imageLink: string;
    isPostLiked: boolean;
}