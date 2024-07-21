package com.project.oriobook.common.configs;

import com.project.oriobook.common.constants.CommonConst;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "Oriobook App in Java Spring boot",
                version = "1.0.0",
                description = "Ecommerce application for training"
        ),
        servers = {
                @Server(url = "http://localhost:${server.port}", description = "Local Development Server"),
        }
)

@SecurityScheme(
        // Can be any name, used to reference this scheme in the @SecurityRequirement annotation
        name = CommonConst.BEARER_KEY,
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
@Configuration
public class OpenApiConfig {
}
