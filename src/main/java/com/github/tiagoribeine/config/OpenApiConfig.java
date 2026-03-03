package com.github.tiagoribeine.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customerOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("LIBRARY REST API WITH SPRING BOOT")
                        .version("v1")
                        .description("LIBRARY REST API WITH SPRING BOOT")
                        .termsOfService("https://github.com/tiagoribeine")
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://github.com/tiagoribeine")
                        ));
    }
}
