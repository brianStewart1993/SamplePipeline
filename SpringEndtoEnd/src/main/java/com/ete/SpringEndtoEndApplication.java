package com.ete;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringEndtoEndApplication {
	private final static Logger log = LoggerFactory.getLogger(SpringEndtoEndApplication.class);

	public static void main(String[] args) {
		log.info("Starting application");
		SpringApplication.run(SpringEndtoEndApplication.class, args);
	}
}
