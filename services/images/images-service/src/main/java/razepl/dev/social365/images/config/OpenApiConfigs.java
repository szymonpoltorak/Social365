package razepl.dev.social365.images.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@OpenAPIDefinition
@SecurityScheme(
        name = "JWT Bearer Token Authorisation",
        description = "Provide a valid JWT token to access this API",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "Bearer {token}",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfigs {

    @Bean
    public OpenAPI customOpenAPI(
            @Value("${openapi.service.title}") String serviceTitle,
            @Value("${openapi.service.version}") String serviceVersion,
            @Value("${openapi.service.url}") String url) {
        return new OpenAPI()
                .servers(List.of(new Server().url(url)))
                .security(List.of(new SecurityRequirement().addList("Jwt Bearer Token Authorisation")))
                .info(
                        new Info()
                                .title(serviceTitle)
                                .version(serviceVersion)
                                .license(new License().name("Apache-2.0 license").url("https://www.apache.org/licenses/"))
                                .contact(new Contact().name("Szymon Półtorak").email("szymonpotorak@gmail.com").url("https://github.com/szymonpoltorak"))
                );
    }

}
