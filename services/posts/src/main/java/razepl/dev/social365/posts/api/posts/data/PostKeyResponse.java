package razepl.dev.social365.posts.api.posts.data;

import razepl.dev.social365.posts.clients.profile.data.Profile;

public record PostKeyResponse(Profile author, String postId, String creationDateTime) {
}
