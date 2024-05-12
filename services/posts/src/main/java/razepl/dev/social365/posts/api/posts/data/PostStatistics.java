package razepl.dev.social365.posts.api.posts.data;

import lombok.Builder;

@Builder
public record PostStatistics(int likes, int comments, int shares) {
}
