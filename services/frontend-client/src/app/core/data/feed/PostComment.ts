export interface PostComment {
    commentId: number;
    commentLikesCount: number;
    content: string;
    authorFullName: string;
    creationDateTime: Date;
    profileImageLink: string;
}
