package com.boot.resvo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 스웨거 환경 설정
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket restAPI() {
		return new Docket(DocumentationType.OAS_30)
			.apiInfo(apiInfo())
			.select()
			.apis(RequestHandlerSelectors.any())
			.paths(PathSelectors.any())
			.build();
	}

	/**
	 * 스웨거 설명
	 * @return
	 */
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
			.title("[Resvo] Spring Boot REST API")
			.version("1.0.0")
			.description("resvo 프로젝트의 swagger api 입니다.")
			.build();
	}
}
