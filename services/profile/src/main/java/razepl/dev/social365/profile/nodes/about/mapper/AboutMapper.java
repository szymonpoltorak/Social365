package razepl.dev.social365.profile.nodes.about.mapper;

import razepl.dev.social365.profile.api.profile.about.overview.data.AboutOptionResponse;
import razepl.dev.social365.profile.nodes.about.birthdate.BirthDate;
import razepl.dev.social365.profile.nodes.about.details.AboutDetails;
import razepl.dev.social365.profile.nodes.about.details.enums.DetailsType;
import razepl.dev.social365.profile.nodes.about.gender.Gender;
import razepl.dev.social365.profile.nodes.about.mail.Email;
import razepl.dev.social365.profile.nodes.about.mobile.Mobile;
import razepl.dev.social365.profile.nodes.about.relationship.RelationshipStatus;
import razepl.dev.social365.profile.nodes.about.workplace.Workplace;

public interface AboutMapper {

    AboutOptionResponse mapWorkplaceToAboutOptionResponse(Workplace workplace);

    AboutOptionResponse mapRelationshipStatusToResponse(RelationshipStatus relationshipStatus);

    AboutOptionResponse mapAboutDetailsToAboutOptionResponse(AboutDetails aboutDetails);

    AboutOptionResponse mapMobileNumberToMobileNumberResponse(Mobile mobile);

    AboutOptionResponse mapGenderToAboutOptionResponse(Gender gender);

    AboutOptionResponse mapEmailToAboutOptionResponse(Email email);

    AboutOptionResponse mapBirthdayToAboutOptionResponse(BirthDate birthday);

}
