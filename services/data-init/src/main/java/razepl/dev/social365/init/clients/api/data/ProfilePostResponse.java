package razepl.dev.social365.init.clients.api.data;

import lombok.Builder;

@Builder
public record ProfilePostResponse(String profileId, String username, String fullName,
                                  String subtitle, String profilePictureUrl) {
}
