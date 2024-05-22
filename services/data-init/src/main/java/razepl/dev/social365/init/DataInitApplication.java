package razepl.dev.social365.init;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class DataInitApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataInitApplication.class, args);
	}

}
