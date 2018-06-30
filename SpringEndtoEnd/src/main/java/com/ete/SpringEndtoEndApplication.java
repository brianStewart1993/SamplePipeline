package com.ete;

import org.slf4j.Logger;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringEndtoEndApplication extends SpringBootServletInitializer {
	private final static Logger log = LoggerFactory.getLogger(SpringEndtoEndApplication.class);
	
	 @Override
	    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
	        return application.sources(SpringEndtoEndApplication.class);
	    }

	public static void main(String[] args) {
		log.info("Starting application");
		SpringApplication.run(SpringEndtoEndApplication.class, args);
	}
}
