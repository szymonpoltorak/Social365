import { User } from "@interfaces/feed/user.interface";

export interface Profile extends User {
    numberOfFollowers: number;
    description: string;
    postCount: number;
    numberOfFriends: number;
}
