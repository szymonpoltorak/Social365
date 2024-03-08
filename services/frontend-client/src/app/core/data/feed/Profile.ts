import { Author } from "@core/data/feed/Author";

export interface Profile extends Author {
    numberOfFollowers: number;
    description: string;
    postCount: number;
    numberOfFriends: number;
}
