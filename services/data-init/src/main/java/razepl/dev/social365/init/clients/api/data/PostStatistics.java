package razepl.dev.social365.init.clients.api.data;

import lombok.Builder;

@Builder
public record PostStatistics(int likes, int comments, int shares) {
}
