package lazarbosnjak.kucniBudzet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableJpaAuditing
@EnableAsync
public class KucniBudzetApplication {

	public static void main(String[] args) {
		SpringApplication.run(KucniBudzetApplication.class, args);
	}

}
