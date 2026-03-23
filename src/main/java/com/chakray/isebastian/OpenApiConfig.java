package com.chakray.isebastian;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "API REST - Irving Sebastián",
        version = "1.0",
        description = "API REST para prueba técnica - CHAKRAY"
    )
)
public class OpenApiConfig {

}
