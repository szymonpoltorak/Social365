package razepl.dev.social365.profile.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UsersAreNotFriendsException extends ResponseStatusException {
    public UsersAreNotFriendsException(String profileId, String friendId) {
        super(HttpStatus.BAD_REQUEST,
                String.format("Users with profileId: %s and friendId: %s are not friends", profileId, friendId));
    }
}
