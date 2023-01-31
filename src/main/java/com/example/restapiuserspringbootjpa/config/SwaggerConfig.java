package com.example.restapiuserspringbootjpa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

/**
 * Swagger configuration for REST API documentation <p>
 * HTML = http://localhost:8080/swagger-ui/index.html <p>
 * JSON = http://localhost:8080/v2/api-docs
 */
@Configuration
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiDetails());
    }

    private ApiInfo apiDetails() {
        return new ApiInfo("REST API of users on Spring Boot",
                "Library REST API docs",
                "1.0",
                "https://google.com",
                new Contact("Alexander",
                        "https://google.com",
                        "https://google.com"),
                "MIT",
                "https://google.com",
                Collections.emptyList());
    }
}
