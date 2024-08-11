package razepl.dev.social365.auth.clients.profile;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import razepl.dev.social365.auth.clients.profile.constants.ProfileMappings;
import razepl.dev.social365.auth.clients.profile.data.Profile;
import razepl.dev.social365.auth.clients.profile.data.ProfileRequest;

@FeignClient(name = "PROFILE-SERVICE", url = "http://profile-service:8083")
public interface ProfileService {

    @PostMapping(value = ProfileMappings.CREATE_USERS_PROFILE_MAPPING)
    Profile createUsersProfile(@RequestBody ProfileRequest profileRequest);

    @GetMapping(value = ProfileMappings.GET_BASIC_PROFILE_INFO_MAPPING)
    Profile getBasicProfileInfo();

}
