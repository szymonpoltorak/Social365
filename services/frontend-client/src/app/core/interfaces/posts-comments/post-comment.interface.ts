import { CommentKey } from "@interfaces/posts-comments/comment-key.interface";
import { Comment } from "@interfaces/posts-comments/comment.interface";
import { Profile } from "@interfaces/feed/profile.interface";
import { Optional } from "@core/types/profile/optional.type";
import { AttachImage } from "@interfaces/feed/attach-image.interface";

export class PostComment implements Comment {

    private _commentKey: CommentKey;
    private _author: Profile;
    private _content: string;
    private _hasReplies: boolean;
    private _imageUrl: string;
    private _isLiked: boolean;
    private _commentLikesCount: number;

    constructor(commentKey: CommentKey, author: Profile, content: string,
                hasReplies: boolean, imageUrl: string, isLiked: boolean, commentLikesCount: number) {
        this._commentKey = commentKey;
        this._author = author;
        this._content = content;
        this._hasReplies = hasReplies;
        this._imageUrl = imageUrl;
        this._isLiked = isLiked;
        this._commentLikesCount = commentLikesCount;
    }

    isPostComment(): boolean {
        return true;
    }

    updateCommentContent(content: string, attachImage: Optional<AttachImage>): void {
        this._content = content;
        this._imageUrl = attachImage !== null ? attachImage.fileUrl : "";
    }

    updateLikesCount(): void {
        this._isLiked = !this._isLiked;

        this._commentLikesCount = this._isLiked ? this._commentLikesCount + 1 : this._commentLikesCount - 1;
    }

    get commentKey(): CommentKey {
        return this._commentKey;
    }

    get author(): Profile {
        return this._author;
    }

    get content(): string {
        return this._content;
    }

    set content(value: string) {
        this._content = value;
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

}
