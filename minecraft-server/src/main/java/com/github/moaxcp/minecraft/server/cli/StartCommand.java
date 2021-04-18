package com.github.moaxcp.minecraft.server.cli;

import lombok.Builder;
import lombok.Singular;
import lombok.Value;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Value
@Builder
public class StartCommand {
  String javaCommand = "java";
  @Singular
  List<JavaArgument> javaArguments;
  File serverJar;
  @Singular
  List<MinecraftArgument> minecraftArguments;

  public List<String> toCommand() {
    List<String> command = new ArrayList<>();
    command.add(javaCommand);
    command.addAll(javaArguments.stream()
        .flatMap(j -> j.forCommandLine().stream())
        .collect(toList()));
    command.add("-jar");
    command.add(serverJar.getAbsolutePath());
    command.addAll(minecraftArguments.stream()
      .flatMap(m -> m.forCommandLine().stream())
      .collect(toList()));
    return command;
  }
}
