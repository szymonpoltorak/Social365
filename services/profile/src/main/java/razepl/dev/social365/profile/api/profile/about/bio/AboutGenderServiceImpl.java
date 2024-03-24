package razepl.dev.social365.profile.api.profile.about.bio;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import razepl.dev.social365.profile.api.profile.about.bio.interfaces.AboutGenderService;
import razepl.dev.social365.profile.api.profile.about.old.data.GenderRequest;
import razepl.dev.social365.profile.api.profile.data.ProfileRequest;
import razepl.dev.social365.profile.exceptions.ProfileNotFoundException;
import razepl.dev.social365.profile.nodes.about.gender.Gender;
import razepl.dev.social365.profile.nodes.profile.Profile;
import razepl.dev.social365.profile.nodes.profile.interfaces.ProfileMapper;
import razepl.dev.social365.profile.nodes.profile.interfaces.ProfileRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class AboutGenderServiceImpl implements AboutGenderService {

    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;

    @Override
    public final ProfileRequest updateProfileGender(GenderRequest genderRequest) {
        log.info("New gender data : {}", genderRequest);

        Profile profile = profileRepository.findById(genderRequest.profileId())
                .orElseThrow(ProfileNotFoundException::new);

        log.info("Profile found in repository from request : {}", profile);

        Gender gender = profile.getGender();

        gender.setGenderType(genderRequest.gender());
        gender.setPrivacyLevel(genderRequest.privacyLevel());

        profile.setGender(gender);

        profile = profileRepository.save(profile);

        return profileMapper.mapProfileToProfileRequest(profile);
    }

    @Override
    public final ProfileRequest deleteProfileGender(String profileId) {
        return null;
    }
}
