package razepl.dev.social365.notifications.config.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class UserConverter implements Converter<Jwt, User> {

    @Override
    public final User convert(Jwt jwt) {
        return User
                .builder()
                .profileId(jwt.getClaimAsString(JwtClaims.PROFILE_ID))
                .userId(jwt.getClaim(JwtClaims.USER_ID))
                .username(jwt.getSubject())
                .build();
    }

}
