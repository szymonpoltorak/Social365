package razepl.dev.social365.init.clients;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class FeignJwtInterceptor implements RequestInterceptor {

    private static final String AUTH_HEADER = "Authorization";
    private static final String TOKEN_HEADER = "Bearer ";

    @Override
    public final void apply(RequestTemplate template) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof JwtAuthenticationToken jwtAuthenticationToken) {
            template.header(
                    AUTH_HEADER,
                    String.format("%s%s", TOKEN_HEADER, jwtAuthenticationToken.getToken().getTokenValue())
            );
        }
    }

}
