import { Author } from "@core/data/feed/Author";

export interface Profile extends Author {
    description: string;
    postCount: number;
    numberOfFriends: number;
}
