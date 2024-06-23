import { Optional } from "@core/types/profile/optional.type";
import { AboutOptionData } from "@interfaces/profile/about/about-option-data.interface";

export interface OverviewData {
    workplace : Optional<AboutOptionData>;
    highSchool : Optional<AboutOptionData>;
    currentCity : Optional<AboutOptionData>;
    homeTown : Optional<AboutOptionData>;
    relationshipStatus : Optional<AboutOptionData>;
}
