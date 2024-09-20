package razepl.dev.social365.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import razepl.dev.social365.auth.config.jwt.interfaces.JwtService;

@Slf4j
@EnableFeignClients
@SpringBootApplication
public class AuthServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(JwtService jwtService) {
        return args -> log.info("Kafka JWT : {}", jwtService.generateKafkaJwtToken());
    }

}
