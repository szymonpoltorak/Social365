package razepl.dev.social365.posts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.CassandraContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration(proxyBeanMethods = false)
public class TestPostsApplication {

	@Bean
	@ServiceConnection
	CassandraContainer<?> cassandraContainer() {
		return new CassandraContainer<>(DockerImageName.parse("cassandra:latest"));
	}

	public static void main(String[] args) {
		SpringApplication.from(PostsApplication::main).with(TestPostsApplication.class).run(args);
	}

}
