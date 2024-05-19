package razepl.dev.social365.profile.clients.posts.comments.data;

import lombok.Builder;

@Builder
public record PostStatistics(int likes, int comments, int shares) {
}
