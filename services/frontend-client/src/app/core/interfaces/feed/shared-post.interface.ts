import { Post } from "@interfaces/feed/post.interface";

export class SharedPost {
    sharingPost: Post;
    sharedPost: Post;

    constructor(sharingPost: Post, sharedPost: Post) {
        this.sharingPost = sharingPost;
        this.sharedPost = sharedPost;
    }

    get author() {
        return this.sharingPost.author;
    }

    get imageLinks() {
        return this.sharedPost.imageLinks;
    }
}
