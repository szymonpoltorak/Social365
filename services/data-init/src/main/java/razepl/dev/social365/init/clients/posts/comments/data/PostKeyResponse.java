package razepl.dev.social365.init.clients.posts.comments.data;


import razepl.dev.social365.init.clients.profile.data.Profile;

public record PostKeyResponse(Profile author, String postId, String creationDateTime) {
}
