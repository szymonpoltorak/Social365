import { Optional } from "@core/types/profile/Optional";
import { AboutOptionData } from "@core/data/profile/about/AboutOptionData";

export interface OverviewData {
    workplace : Optional<AboutOptionData>;
    school : Optional<AboutOptionData>;
    currentCity : Optional<AboutOptionData>;
    hometown : Optional<AboutOptionData>;
    relationship : Optional<AboutOptionData>;
}
