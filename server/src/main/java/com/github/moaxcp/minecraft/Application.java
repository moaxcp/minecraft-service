package com.github.moaxcp.minecraft;

import io.micronaut.runtime.Micronaut;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@OpenAPIDefinition(info = @Info(
    title = "Minecraft Server",
    version = "0.0",
    description = "API for managing a minecraft server",
    license = @License(name = "MIT", url = ""),
    contact = @Contact(url = "moaxcp.github.io", name = "John Mercier")
), security = @SecurityRequirement(name = "basic"))
public class Application {

    public static void main(String[] args) {
        Micronaut.run(Application.class, args);
    }
}
