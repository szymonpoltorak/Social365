package razepl.dev.social365.profile.kafka.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import razepl.dev.social365.profile.config.kafka.KafkaConfigNames;
import razepl.dev.social365.profile.nodes.profile.Profile;
import razepl.dev.social365.profile.nodes.profile.interfaces.ProfileRepository;
import razepl.dev.social365.profile.utils.exceptions.ProfileNotFoundException;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumer {

    private final ProfileRepository profileRepository;

    @KafkaListener(
            topics = KafkaConfigNames.ACCOUNT_LOGOUT_TOPIC,
            groupId = KafkaConfigNames.NOTIFICATIONS_GROUP_ID,
            properties = KafkaConfigNames.ACCOUNT_LOGOUT_EVENT_TYPE_PROPERTY
    )
    public final void consumeAccountLogout(AccountLogoutEvent event) {
        log.info("Consumed account.logout event: {}", event);

        Profile profile = profileRepository.findByUsername(event.username())
                .orElseThrow(ProfileNotFoundException::new);

        log.info("Profile matching event : {}", profile);

        profile.setOnline(false);

        profileRepository.save(profile);
    }

}
