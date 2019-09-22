package jkin.be.kudosstats;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@EnableJpaRepositories(basePackages = {"jkin.be.kudosstats"})
public class KudosstatsApplication {

	public static void main(String[] args) {
		SpringApplication.run(KudosstatsApplication.class, args);
	}

}
