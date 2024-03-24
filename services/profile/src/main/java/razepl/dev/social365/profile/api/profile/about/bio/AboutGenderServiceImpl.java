package razepl.dev.social365.profile.api.profile.about.bio;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import razepl.dev.social365.profile.api.profile.about.bio.interfaces.AboutGenderService;
import razepl.dev.social365.profile.api.profile.about.old.data.GenderRequest;
import razepl.dev.social365.profile.api.profile.data.ProfileRequest;
import razepl.dev.social365.profile.exceptions.ProfileNotFoundException;
import razepl.dev.social365.profile.nodes.about.gender.Gender;
import razepl.dev.social365.profile.nodes.about.gender.GenderRepository;
import razepl.dev.social365.profile.nodes.profile.Profile;
import razepl.dev.social365.profile.nodes.profile.interfaces.ProfileMapper;
import razepl.dev.social365.profile.nodes.profile.interfaces.ProfileRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class AboutGenderServiceImpl implements AboutGenderService {

    private static final String PROFILE_FOUND_IN_REPOSITORY_FROM_REQUEST = "Profile found in repository from request : {}";

    private final ProfileRepository profileRepository;
    private final GenderRepository genderRepository;
    private final ProfileMapper profileMapper;

    @Override
    public final ProfileRequest updateProfileGender(GenderRequest genderRequest) {
        log.info("New gender data : {}", genderRequest);

        Profile profile = profileRepository.findById(genderRequest.profileId())
                .orElseThrow(ProfileNotFoundException::new);

        log.info(PROFILE_FOUND_IN_REPOSITORY_FROM_REQUEST, profile);

        Gender gender = profile.getGender();

        gender.setGenderType(genderRequest.gender());
        gender.setPrivacyLevel(genderRequest.privacyLevel());

        genderRepository.save(gender);

        return profileMapper.mapProfileToProfileRequest(profile);
    }

    @Override
    public final ProfileRequest deleteProfileGender(String profileId) {
        log.info("Deleting gender for profile id : {}", profileId);

        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(ProfileNotFoundException::new);

        log.info(PROFILE_FOUND_IN_REPOSITORY_FROM_REQUEST, profile);

        genderRepository.deleteById(profile.getGender().getGenderId());

        return profileMapper.mapProfileToProfileRequest(profile);
    }
}
