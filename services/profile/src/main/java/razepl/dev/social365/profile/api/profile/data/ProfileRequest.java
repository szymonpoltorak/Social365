package razepl.dev.social365.profile.api.profile.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import razepl.dev.social365.profile.api.profile.constants.Params;

import java.time.LocalDate;

@Builder
public record ProfileRequest(@JsonProperty(Params.USER_ID) long userId,
                             @JsonProperty(Params.USERNAME) String username,
                             @JsonProperty(Params.FIRST_NAME) String firstName,
                             @JsonProperty(Params.LAST_NAME) String lastName,
                             @JsonProperty(Params.DATE_OF_BIRTH) String dateOfBirth) {

    @JsonIgnore
    public LocalDate getDateOfBirth() {
        return LocalDate.parse(dateOfBirth);
    }

}
