package com.microservices.employee_service.commons.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Rahul",
                        email = "rahulakula2015@gmail.com",
                        url = "https://www.linkedin.com/in/rahul-rao-akula/"
                ),
                description = "Employee Service Documentation",
                title = "Employee Service Api's",
                version = "1.0",
                license = @License(
                        name = "License name",
                        url = "https://www.linkedin.com/in/rahul-rao-akula/"
                ),
                termsOfService = "Terms of service"
        ),
        /*servers = {
                @in.sjsolutions.trypmaker.Server(
                description = "Local ENV",
                url = "http://localhost:8080"
                ),

                @in.sjsolutions.trypmaker.Server(
                description = "Dev ENV",
                url = "http://43.205.13.8:80888"
                 ),
                @in.sjsolutions.trypmaker.Server(
                        description = "PROD ENV",
                        url = ""
                )
        },*/
        security = {
                @SecurityRequirement(
                        name = "bearerAuth"
                )
        }
)
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT Auth description",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {

}

