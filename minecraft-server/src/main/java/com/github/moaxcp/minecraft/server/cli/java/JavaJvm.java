package com.github.moaxcp.minecraft.server.cli.java;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.nio.file.Path;

@Value
@Builder
@Jacksonized
public class JavaJvm {
  Path command;
  String version;
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
