package za.gov.dpw.iarts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class IartsBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(IartsBackendApplication.class, args);
	}

}
