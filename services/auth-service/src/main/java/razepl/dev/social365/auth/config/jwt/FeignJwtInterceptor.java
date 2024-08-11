package razepl.dev.social365.auth.config.jwt;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import razepl.dev.social365.auth.config.constants.Headers;

@Component
public class FeignJwtInterceptor implements RequestInterceptor {

    @Override
    public final void apply(RequestTemplate template) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof JwtAuthenticationToken jwtAuthenticationToken) {
            template.header(
                    Headers.AUTH_HEADER,
                    String.format("%s%s", Headers.TOKEN_HEADER, jwtAuthenticationToken.getToken().getTokenValue())
            );
        }
    }

}
