import { Post } from "@interfaces/feed/post.interface";

export class SharedPost {

    private _sharingPost: Post;
    private _sharedPost: Post;

    constructor(sharingPost: Post, sharedPost: Post) {
        this._sharingPost = sharingPost;
        this._sharedPost = sharedPost;
    }

    static fromJson(json: any): SharedPost {
        return new SharedPost(
            Post.fromJson(json.sharingPost),
            Post.fromJson(json.sharedPost)
        );
    }

    get sharingPost(): Post {
        return this._sharingPost;
    }

    get sharedPost(): Post {
        return this._sharedPost;
    }

}
