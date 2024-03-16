import { Optional } from "@core/types/profile/optional.type";
import { AboutOptionData } from "@interfaces/profile/about/about-option-data.interface";

export interface OverviewData {
    workplace : Optional<AboutOptionData>;
    school : Optional<AboutOptionData>;
    currentCity : Optional<AboutOptionData>;
    hometown : Optional<AboutOptionData>;
    relationship : Optional<AboutOptionData>;
}
