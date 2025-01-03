import { ReplyKey } from "@interfaces/posts-comments/reply-key.interface";
import { Comment } from "@interfaces/posts-comments/comment.interface";
import { Profile } from "@interfaces/feed/profile.interface";
import { Optional } from "@core/types/profile/optional.type";
import { AttachImage } from "@interfaces/feed/attach-image.interface";

export class ReplyComment implements Comment {

    private _commentKey: ReplyKey;
    private _author: Profile;
    private _likesCount: number;
    private _content: string;
    private _hasReplies: boolean;
    private _imageUrl: string;
    private _isLiked: boolean;
    private _commentLikesCount: number;

    constructor(commentKey: ReplyKey, author: Profile, likesCount: number, content: string,
                hasReplies: boolean, imageUrl: string, isLiked: boolean, commentLikesCount: number) {
        this._commentKey = commentKey;
        this._author = author;
        this._likesCount = likesCount;
        this._content = content;
        this._hasReplies = hasReplies;
        this._imageUrl = imageUrl;
        this._isLiked = isLiked;
        this._commentLikesCount = commentLikesCount;
    }

    updateCommentContent(content: string, attachImage: Optional<AttachImage>): void {
        this._content = content;
        this._imageUrl = attachImage !== null ? attachImage.fileUrl : "";
    }

    isPostComment(): boolean {
        return false;
    }

    updateLikesCount(): void {
        this._isLiked = !this._isLiked;

        this._commentLikesCount = this._isLiked ? this._commentLikesCount + 1 : this._commentLikesCount - 1;
    }

    set hasReplies(value: boolean) {
        this._hasReplies = value;
    }

    get commentKey(): ReplyKey {
        return this._commentKey;
    }

    get author(): Profile {
        return this._author;
    }

    get likesCount(): number {
        return this._likesCount;
    }

    get content(): string {
        return this._content;
    }

    get hasReplies(): boolean {
        return this._hasReplies;
    }

    get imageUrl(): string {
        return this._imageUrl;
    }

    set imageUrl(value: string) {
        this._imageUrl = value;
    }

    get isLiked(): boolean {
        return this._isLiked;
    }

    get commentLikesCount(): number {
        return this._commentLikesCount;
    }

    static fromJson(json: any): ReplyComment {
        return new ReplyComment(
            json.commentKey,
            json.author,
            json.likesCount,
            json.content,
            json.hasReplies,
            json.imageUrl,
            json.isLiked,
            json.commentLikesCount
        );
    }
}
