package com.todo.config;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.info.InfoContributorAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(InfoContributorAutoConfiguration.class)

public class OpenApiConfig {
    @Value("${info.myapp.version}")
    private String appVersion;

    private static final String SECURITY_SCHEMA_NAME = "Bearer Authentication";


    @Bean
    public OpenAPI baseOpenAPI(){
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement()
                        .addList(SECURITY_SCHEMA_NAME))
                .components(new Components()
                        .addSecuritySchemes(SECURITY_SCHEMA_NAME, createAPIKeyScheme()))
                .info(new Info()
                        .title("ToDo API")
                        .description("Second Spring lab (with Spring Security")
                        .version(appVersion));
    }

    @Bean
    public SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme()
                .name(SECURITY_SCHEMA_NAME)
                .type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer");
    }

}
