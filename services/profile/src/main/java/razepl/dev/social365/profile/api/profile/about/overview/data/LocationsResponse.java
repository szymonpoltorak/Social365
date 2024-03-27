package razepl.dev.social365.profile.api.profile.about.overview.data;

import lombok.Builder;

@Builder
public record LocationsResponse(AboutOptionResponse homeTown, AboutOptionResponse currentCity) {
}
