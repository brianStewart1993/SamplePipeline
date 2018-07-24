package com.ete.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfig {
	
	@Bean
	public Docket productApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.ete.controllers"))
				.build();
	}
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Users Api")
				.description("Sample Api to interact with a users database")
				.termsOfServiceUrl("http://www.brian.gq")
				.license("Brian Stewart License")
				.licenseUrl("brian.stewart@ingenuitytech.co").version("1.0").build();
	}
}
