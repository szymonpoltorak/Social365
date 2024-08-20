package razepl.dev.social365.images.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class UserConverter implements Converter<Jwt, User> {

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
