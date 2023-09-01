package com.github.moaxcp.minecraft.server.cli;

import com.github.moaxcp.minecraft.server.cli.java.JavaArgument;
import com.github.moaxcp.minecraft.server.cli.minecraft.MinecraftArgument;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Value
@Builder(toBuilder = true)
@Serdeable
public class StartCommand {
  @NonNull
  Path serverDirectory;
  @NonNull
  Path javaCommand;
  @Singular
  List<JavaArgument> javaArguments;
  @NonNull
  Path serverJar;
  @Singular
  List<MinecraftArgument> minecraftArguments;

  public List<String> toCommand() {
    List<String> command = new ArrayList<>();
    command.add(javaCommand.normalize().toString());
    command.addAll(javaArguments.stream()
        .flatMap(j -> j.getArguments().stream())
        .toList());
    command.add("-jar");
    command.add(serverJar.normalize().toString());
    command.addAll(minecraftArguments.stream()
      .flatMap(m -> m.getArguments().stream())
      .toList());
    return command;
  }
}
