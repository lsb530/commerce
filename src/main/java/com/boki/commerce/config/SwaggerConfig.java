package com.boki.commerce.config;

import com.boki.commerce.resolver.LoginUser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
            .apiInfo(apiInfo())
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.boki.commerce.api"))
            .paths(PathSelectors.any())
            .build()
            .ignoredParameterTypes(LoginUser.class)
            .useDefaultResponseMessages(false);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("Commerce Swagger Open API Docs")
            .description("Commerce API Specification</a>")
            .contact(new Contact("boki","https://code-boki.tistory.com","lsb530@naver.com"))
            .version("1.0")
            .termsOfServiceUrl("http://swagger.io/terms/")
            .build();
    }
}