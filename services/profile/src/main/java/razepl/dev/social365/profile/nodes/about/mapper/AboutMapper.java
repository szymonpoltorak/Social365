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

    AboutOptionResponse mapWorkplaceToAboutOptionResponse(String profileId);

    AboutOptionResponse mapWorkplaceToAboutOptionResponse(Workplace workplace);

    AboutOptionResponse mapRelationshipStatusToResponse(String profileId);

    AboutOptionResponse mapRelationshipStatusToResponse(RelationshipStatus relationshipStatus);

    AboutOptionResponse mapAboutDetailsToAboutOptionResponse(String profileId, DetailsType detailsType);

    AboutOptionResponse mapAboutDetailsToAboutOptionResponse(AboutDetails aboutDetails);

    AboutOptionResponse mapMobileNumberToMobileNumberResponse(String profileId);

    AboutOptionResponse mapMobileNumberToMobileNumberResponse(Mobile mobile);

    AboutOptionResponse mapGenderToAboutOptionResponse(String profileId);

    AboutOptionResponse mapGenderToAboutOptionResponse(Gender gender);

    AboutOptionResponse mapEmailToAboutOptionResponse(String profileId);

    AboutOptionResponse mapEmailToAboutOptionResponse(Email email);

    AboutOptionResponse mapBirthdayToAboutOptionResponse(String profileId);

    AboutOptionResponse mapBirthdayToAboutOptionResponse(BirthDate birthday);

}
