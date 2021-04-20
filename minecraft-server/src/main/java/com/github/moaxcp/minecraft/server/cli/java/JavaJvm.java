package com.github.moaxcp.minecraft.server.cli.java;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class JavaJvm {
  String command;
  String version;
  String home;

  public static JavaJvm detectCurrentJvm() {
    String command = ProcessHandle.current().info().command().orElseThrow();
    String version = System.getProperty("java.version");
    String home = System.getProperty("java.home");
    return JavaJvm.builder()
        .command(command)
        .home(home)
        .version(version)
        .build();
  }
}
