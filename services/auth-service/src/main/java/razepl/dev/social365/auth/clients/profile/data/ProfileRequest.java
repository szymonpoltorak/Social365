package razepl.dev.social365.auth.clients.profile.data;

import lombok.Builder;
import razepl.dev.social365.auth.entities.user.User;

import java.time.LocalDate;

@Builder
public record ProfileRequest(long userId, String username, String firstName, String lastName, LocalDate dateOfBirth) {

    public static ProfileRequest of(User user, LocalDate dateOfBirth) {
        return ProfileRequest.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .firstName(user.getName())
                .lastName(user.getSurname())
                .dateOfBirth(dateOfBirth)
                .build();
    }

}
