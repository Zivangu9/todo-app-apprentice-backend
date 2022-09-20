package apprentice.ivan.todoappbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.map.repository.config.EnableMapRepositories;

@SpringBootApplication
@EnableMapRepositories
public class TodoAppBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodoAppBackendApplication.class, args);
	}

}
