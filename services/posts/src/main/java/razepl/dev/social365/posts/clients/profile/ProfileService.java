package razepl.dev.social365.posts.clients.profile;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import razepl.dev.social365.posts.api.constants.Params;
import razepl.dev.social365.posts.clients.profile.constants.ProfileMappings;
import razepl.dev.social365.posts.clients.profile.data.Profile;
import razepl.dev.social365.posts.utils.handlers.MicroserviceErrorDecoder;
import razepl.dev.social365.posts.utils.pagination.data.ProfileSocialPage;

@FeignClient(name = "PROFILE-SERVICE", url = "http://profile-service:8083", configuration = {MicroserviceErrorDecoder.class})
public interface ProfileService {

    @GetMapping(value = ProfileMappings.GET_FOLLOWED_IDS)
    ProfileSocialPage<String> getFriendsIds(@RequestParam(Params.PROFILE_ID) String profileId,
                                            @RequestParam(Params.PAGE_NUMBER) int pageNumber);

    @GetMapping(value = ProfileMappings.GET_POST_PROFILE_INFO_MAPPING)
    Profile getProfileDetails(@RequestParam(Params.PROFILE_ID) String profileId);

}
