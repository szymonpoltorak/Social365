package razepl.dev.social365.profile.nodes.about.mapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import razepl.dev.social365.profile.api.profile.about.overview.data.AboutOptionResponse;
import razepl.dev.social365.profile.exceptions.ProfileDetailsNotFoundException;
import razepl.dev.social365.profile.nodes.about.birthdate.BirthDate;
import razepl.dev.social365.profile.nodes.about.birthdate.BirthDateRepository;
import razepl.dev.social365.profile.nodes.about.details.AboutDetails;
import razepl.dev.social365.profile.nodes.about.details.enums.DetailsType;
import razepl.dev.social365.profile.nodes.about.details.interfaces.AboutDetailsRepository;
import razepl.dev.social365.profile.nodes.about.gender.Gender;
import razepl.dev.social365.profile.nodes.about.gender.GenderRepository;
import razepl.dev.social365.profile.nodes.about.mail.Email;
import razepl.dev.social365.profile.nodes.about.mail.interfaces.EmailRepository;
import razepl.dev.social365.profile.nodes.about.mobile.Mobile;
import razepl.dev.social365.profile.nodes.about.mobile.interfaces.MobileRepository;
import razepl.dev.social365.profile.nodes.about.relationship.RelationshipStatus;
import razepl.dev.social365.profile.nodes.about.relationship.interfaces.RelationshipStatusRepository;
import razepl.dev.social365.profile.nodes.about.workplace.Workplace;
import razepl.dev.social365.profile.nodes.about.workplace.interfaces.WorkplaceRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class AboutMapperImpl implements AboutMapper {

    @Override
    public final AboutOptionResponse mapWorkplaceToAboutOptionResponse(Workplace workplace) {
        if (workplace == null) {
            log.info("Workplace is null");

            return null;
        }
        return AboutOptionResponse
                .builder()
                .label(workplace.getSubtitle())
                .privacyLevel(workplace.getPrivacyLevel())
                .build();
    }

    @Override
    public final AboutOptionResponse mapRelationshipStatusToResponse(RelationshipStatus relationshipStatus) {
        if (relationshipStatus == null) {
            log.info("Relationship status is null");

            return null;
        }
        return AboutOptionResponse
                .builder()
                .label(relationshipStatus.getRelationshipStatus().name())
                .privacyLevel(relationshipStatus.getPrivacyLevel())
                .build();
    }

    @Override
    public final AboutOptionResponse mapAboutDetailsToAboutOptionResponse(AboutDetails aboutDetails) {
        if (aboutDetails == null) {
            log.info("About details is null");

            return null;
        }
        return AboutOptionResponse
                .builder()
                .privacyLevel(aboutDetails.getPrivacyLevel())
                .label(aboutDetails.getPropertyValue())
                .build();
    }

    @Override
    public final AboutOptionResponse mapMobileNumberToMobileNumberResponse(Mobile mobile) {
        if (mobile == null) {
            log.info("Mobile number is null");

            return null;
        }
        return AboutOptionResponse
                .builder()
                .label(mobile.getPhoneNumber())
                .privacyLevel(mobile.getPrivacyLevel())
                .build();
    }

    @Override
    public final AboutOptionResponse mapGenderToAboutOptionResponse(Gender gender) {
        if (gender == null) {
            log.info("Gender is null");

            return null;
        }
        return AboutOptionResponse
                .builder()
                .label(gender.getGenderType().name())
                .privacyLevel(gender.getPrivacyLevel())
                .build();
    }

    @Override
    public final AboutOptionResponse mapEmailToAboutOptionResponse(Email email) {
        if (email == null) {
            log.info("Email is null");

            return null;
        }
        return AboutOptionResponse
                .builder()
                .label(email.getEmailValue())
                .privacyLevel(email.getPrivacyLevel())
                .build();
    }

    @Override
    public final AboutOptionResponse mapBirthdayToAboutOptionResponse(BirthDate birthday) {
        if (birthday == null) {
            log.info("Birthday is null");

            return null;
        }
        return AboutOptionResponse
                .builder()
                .label(birthday.getDateOfBirth())
                .privacyLevel(birthday.getPrivacyLevel())
                .build();
    }
}
