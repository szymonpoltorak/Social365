package razepl.dev.social365.profile.api.profile.data;

import lombok.Builder;

@Builder
public record BirthdayInfoResponse(ProfileResponse profile, int age) {
}
