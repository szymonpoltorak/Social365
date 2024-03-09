import { User } from "@core/data/feed/User";

export interface Profile extends User {
    numberOfFollowers: number;
    description: string;
    postCount: number;
    numberOfFriends: number;
}
