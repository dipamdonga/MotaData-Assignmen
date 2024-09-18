package com.assignment.dipam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class DipamApplication {

	public static void main(String[] args) {
		SpringApplication.run(DipamApplication.class, args);
	}

}
