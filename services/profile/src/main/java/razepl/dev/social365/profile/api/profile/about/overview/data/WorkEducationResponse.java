package razepl.dev.social365.profile.api.profile.about.overview.data;

import lombok.Builder;

@Builder
public record WorkEducationResponse(AboutOptionResponse workplace, AboutOptionResponse college,
                                    AboutOptionResponse highSchool) {
}
