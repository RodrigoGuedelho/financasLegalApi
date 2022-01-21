package br.com.guedelho.core;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;

import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SpringFoxConfig {
	
	@Bean
	public Docket swagger() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.build();
	}
	
	private ApiInfo apiInfo() {
		return new ApiInfo(
				"API Finanças Legal", 
				"Documentaçao  da api", 
				"1.0", 
				"bla bla..", 
				new  Contact("Rodrigo Guedelho", "https://github.com/RodrigoGuedelho", "rodrigohmjj97@hotmail.com"), 
				"bla bla", "https://github.com/RodrigoGuedelho", Collections.emptyList());
	}
}
