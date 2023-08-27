package com.github.moaxcp.minecraft.server.configuration;

import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

import java.nio.file.Path;

@Value
@Builder
@Serdeable
public class JavaJvm {
  @NotNull
  Path command;
  @NotBlank
  String version;
  @NotNull
  Path home;

  public static JavaJvm detectCurrentJvm() {
    String command = ProcessHandle.current().info().command().orElseThrow();
    String version = System.getProperty("java.version");
    String home = System.getProperty("java.home");
    return JavaJvm.builder()
        .command(Path.of(command))
        .home(Path.of(home))
        .version(version)
        .build();
  }
}
