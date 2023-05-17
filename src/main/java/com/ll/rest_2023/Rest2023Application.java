package com.ll.rest_2023;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Rest2023Application {

	public static void main(String[] args) {
		SpringApplication.run(Rest2023Application.class, args);
	}

}
