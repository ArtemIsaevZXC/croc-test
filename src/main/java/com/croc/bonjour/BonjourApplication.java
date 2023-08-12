package com.croc.bonjour;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BonjourApplication {

	public static void main(String[] args) {
		SpringApplication.run(BonjourApplication.class, args);
	}
/*
	@Bean
	public InitializingBean init(){
		return () -> {
			//create...
		};
	}

	private create...
 */
}
