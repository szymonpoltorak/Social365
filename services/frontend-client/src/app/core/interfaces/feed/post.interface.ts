import { PostStatistics } from "@interfaces/feed/post-statistics.interface";
import { PostKey } from "@interfaces/feed/post-key.interface";

export class Post {

    private readonly _postKey: PostKey;
    private _areNotificationTurnedOn: boolean;
    private _isBookmarked: boolean;
    private _content: string;
    private _statistics: PostStatistics;
    private _isPostLiked: boolean;
    private _imageUrls: string[];

    constructor(
        postKey: PostKey,
        areNotificationTurnedOn: boolean,
        isBookmarked: boolean,
        content: string,
        statistics: PostStatistics,
        isPostLiked: boolean,
        imageUrls: string[]
    ) {
        this._postKey = postKey;
        this._areNotificationTurnedOn = areNotificationTurnedOn;
        this._isBookmarked = isBookmarked;
        this._content = content;
        this._statistics = statistics;
        this._isPostLiked = isPostLiked;
        this._imageUrls = imageUrls;
    }

    updateLikeCount(): void {
        this._isPostLiked = !this._isPostLiked;

        this._statistics.likes = this._isPostLiked ? this._statistics.likes + 1 : this._statistics.likes - 1;
    }

    get postKey(): PostKey {
        return this._postKey;
    }

    get areNotificationTurnedOn(): boolean {
        return this._areNotificationTurnedOn;
    }

    set areNotificationTurnedOn(value: boolean) {
        this._areNotificationTurnedOn = value;
    }

    get isBookmarked(): boolean {
        return this._isBookmarked;
    }

    set isBookmarked(value: boolean) {
        this._isBookmarked = value;
    }

    get content(): string {
        return this._content;
    }

    set content(value: string) {
        this._content = value;
    }

    get statistics(): PostStatistics {
        return this._statistics;
    }

    set statistics(value: PostStatistics) {
        this._statistics = value;
    }

    get isPostLiked(): boolean {
        return this._isPostLiked;
    }

    set isPostLiked(value: boolean) {
        this._isPostLiked = value;
    }

    get imageUrls(): string[] {
        return this._imageUrls;
    }

    set imageUrls(value: string[]) {
        this._imageUrls = value;
    }
}
