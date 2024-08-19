package com.library_management.configuration.local;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Hung Anh",
                        email = "hunganhd.work@gmail.com",
                        url = "https://www.linkedin.com/in/hung-anh-duong-546998250/"
                ),
                description = "Library Management API Documentation",
                title = "Library Management",
                version = "1.0.0",
                license = @License(
                        name = "License",
                        url = ""
                ),
                termsOfService = "Term of service"
        ),
        servers = {
                @Server(
                        description = "Local DEV environment",
                        url = "http://localhost:8080/library"
                )
                // can have many servers
        },
        security = {
                @SecurityRequirement(
                        name = "bearerAuth"
                )
        }
)
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT auth",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class SwaggerConfig {
}
