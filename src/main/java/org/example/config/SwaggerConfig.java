package org.example.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "API Documentation",
                version = "2.0.0",
                description = "API for your application"
        )
)
public class SwaggerConfig {
}
