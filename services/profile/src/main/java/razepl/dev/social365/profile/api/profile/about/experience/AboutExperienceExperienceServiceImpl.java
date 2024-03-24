package razepl.dev.social365.profile.api.profile.about.experience;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import razepl.dev.social365.profile.api.profile.about.experience.data.AboutDetailsRequest;
import razepl.dev.social365.profile.api.profile.about.experience.data.WorkPlaceRequest;
import razepl.dev.social365.profile.api.profile.about.experience.interfaces.AboutExperienceService;
import razepl.dev.social365.profile.api.profile.data.ProfileRequest;
import razepl.dev.social365.profile.nodes.profile.interfaces.ProfileMapper;
import razepl.dev.social365.profile.nodes.profile.interfaces.ProfileRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class AboutExperienceExperienceServiceImpl implements AboutExperienceService {

    @Override
    public final ProfileRequest updateProfileCurrentCity(AboutDetailsRequest cityRequest) {
        log.info("Updating profile current city with data: {}", cityRequest);

        return null;
    }

    @Override
    public final ProfileRequest updateProfileHomeTown(AboutDetailsRequest cityRequest) {
        return null;
    }

    @Override
    public final ProfileRequest updateProfileWorkPlace(WorkPlaceRequest workPlaceRequest) {
        return null;
    }

    @Override
    public final ProfileRequest updateProfileEducation(AboutDetailsRequest educationRequest) {
        return null;
    }

    @Override
    public final ProfileRequest deleteProfileCurrentCity(String profileId) {
        return null;
    }

    @Override
    public final ProfileRequest deleteProfileHomeTown(String profileId) {
        return null;
    }

    @Override
    public final ProfileRequest deleteProfileWorkPlace(String profileId) {
        return null;
    }

    @Override
    public final ProfileRequest deleteProfileEducation(String profileId) {
        return null;
    }

}
