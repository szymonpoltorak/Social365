package razepl.dev.social365.profile.api.profile.about.overview.data;

import lombok.Builder;

@Builder
public record ContactInfoResponse(AboutOptionResponse mobile, AboutOptionResponse email,
                                  AboutOptionResponse gender, AboutOptionResponse birthDate) {
}
