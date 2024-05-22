package razepl.dev.social365.init.clients.profile;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import razepl.dev.social365.init.clients.profile.constants.ProfileMappings;
import razepl.dev.social365.init.clients.profile.data.FriendResponse;
import razepl.dev.social365.init.clients.profile.data.ProfileRequest;
import razepl.dev.social365.init.clients.profile.data.ProfileResponse;

@FeignClient(name = "PROFILE-SERVICE", url = "http://localhost:8083")
public interface ProfileService {

    @PostMapping(value = ProfileMappings.CREATE_USERS_PROFILE_MAPPING)
    ProfileResponse createUsersProfile(@RequestBody ProfileRequest profileRequest);


    @PostMapping(value = ProfileMappings.ADD_FRIEND)
    FriendResponse addUserToFriends(@RequestParam("profileId") String profileId,
                                    @RequestParam("friendId") String friendId);
}
