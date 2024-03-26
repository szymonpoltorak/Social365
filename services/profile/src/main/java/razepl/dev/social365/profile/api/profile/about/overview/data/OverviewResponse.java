package razepl.dev.social365.profile.api.profile.about.overview.data;

import lombok.Builder;

@Builder
public record OverviewResponse(AboutOptionResponse workplace, AboutOptionResponse college, AboutOptionResponse highSchool,
                               AboutOptionResponse currentCity, AboutOptionResponse homeTown,
                               AboutOptionResponse relationshipStatus) {
}
