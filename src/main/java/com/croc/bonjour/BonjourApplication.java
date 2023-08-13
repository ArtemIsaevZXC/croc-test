package com.croc.bonjour;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;

@SpringBootApplication
@Configuration
@EnableJpaRepositories
@Import(RepositoryRestMvcConfiguration.class)
public class BonjourApplication {

	public static void main(String[] args) {
		SpringApplication.run(BonjourApplication.class, args);
	}
}
