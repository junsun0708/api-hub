package com.example.apihub.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {
	@Bean
	public OpenAPI openAPI(@Value("${springdoc.version}") String springdocVersion) {
		StringBuilder sb = new StringBuilder();
		sb.append("테스트 외부 연동 REST API").append("/n");
		sb.append("매개변수는 Map<Stirng,Object> 통일 - 변경 가능성 농후").append("/n");
		
		Info info = new Info().title("테스트").version(springdocVersion).description(sb.toString());
		return new OpenAPI().components(new Components()).info(info);
	}
	
	@Bean
	public GroupedOpenApi group1() {
		return GroupedOpenApi.builder().group("그룹1").pathsToMatch("/auth/**").build();
		// .packagesToScan("com.example.swagger").build();
	}

	@Bean
	public GroupedOpenApi group2() {
		return GroupedOpenApi.builder().group("그룹2").pathsToMatch("/excom/**").build();
	}
}
