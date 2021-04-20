package com.github.moaxcp.minecraft.server;

import com.github.moaxcp.minecraft.server.cli.StartCommand;
import com.github.moaxcp.minecraft.server.cli.java.JavaArgument;
import lombok.experimental.UtilityClass;

import static com.github.moaxcp.minecraft.server.cli.java.JavaArgument.*;

@UtilityClass
public class Conventions {
  /**
   * Converts server info into the conventions to start the server.
   * @param server
   * @return
   */
  StartCommand toStartCommand(MinecraftServer server) {
    return StartCommand.builder()
        .serverDirectory(server.getName())
        .javaCommand(server.getJavaJvm().getCommand())
        .javaArgument(maxMemory(server.getJvmSettings().getMaxMemory()))
        .javaArgument(minMemory(server.getJvmSettings().getMinMemory()))
        .javaArgument(threadStackSize(server.getJvmSettings().getThreadStackSize()))

        .build();
  }
}
