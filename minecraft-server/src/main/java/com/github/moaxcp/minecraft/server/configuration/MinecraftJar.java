package com.github.moaxcp.minecraft.server.configuration;

import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.nio.file.Path;

@Value
@Serdeable
public class MinecraftJar {
  @NotBlank
  String version;
  @NotNull
  Path location;
}
