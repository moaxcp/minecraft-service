package com.github.moaxcp.minecraft.server.cli;

import com.github.moaxcp.minecraft.server.cli.java.JavaArgument;
import com.github.moaxcp.minecraft.server.cli.minecraft.MinecraftArgument;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Value
@Builder(toBuilder = true)
public class StartCommand {
  String serverDirectory;
  String javaCommand;
  @Singular
  List<JavaArgument> javaArguments;
  String serverJar;
  @Singular
  List<MinecraftArgument> minecraftArguments;

  public List<String> toCommand() {
    List<String> command = new ArrayList<>();
    command.add(javaCommand);
    command.addAll(javaArguments.stream()
        .flatMap(j -> j.getArguments().stream())
        .collect(toList()));
    command.add("-jar");
    command.add(serverJar);
    command.addAll(minecraftArguments.stream()
      .flatMap(m -> m.getArguments().stream())
      .collect(toList()));
    return command;
  }
}
