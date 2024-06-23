import { AboutDataEmail } from "@interfaces/profile/about/about-data-email.interface";

export interface DateOfBirthRequest extends AboutDataEmail {
    dateOfBirth: Date;
}
