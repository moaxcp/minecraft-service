package com.github.moaxcp.minecraft.server.configuration;

import com.github.moaxcp.minecraft.server.cli.StartCommand;
import lombok.experimental.UtilityClass;

import java.nio.file.Path;

import static com.github.moaxcp.minecraft.server.cli.java.JavaArgument.*;
import static com.github.moaxcp.minecraft.server.cli.minecraft.MinecraftArgument.nogui;

@UtilityClass
public class Conventions {

  /**
   * Converts server info into the conventions to start the server.
   * @param server
   * @return
   */
  public StartCommand toStartCommand(Path baseDirectory, MinecraftConfiguration server) {
    StartCommand.StartCommandBuilder builder = StartCommand.builder()
        .serverDirectory(baseDirectory.resolve(server.getServerName()))
        .serverJar(baseDirectory.resolve(server.getMinecraftJar().getLocation()))
        .javaCommand(server.getJavaJvm().getCommand())
        .minecraftArgument(nogui());
    if(server.getJvmSettings() != null) {
      JvmSettings jvmSettings = server.getJvmSettings();
      if(jvmSettings.getMaxMemory() != null) {
        builder.javaArgument(maxMemory(server.getJvmSettings().getMaxMemory()));

      }
      if(jvmSettings.getMinMemory() != null) {
        builder.javaArgument(minMemory(server.getJvmSettings().getMinMemory()));
      }
      if(jvmSettings.getThreadStackSize() != null) {
        builder.javaArgument(threadStackSize(server.getJvmSettings().getThreadStackSize()));
      }
    }

    return builder.build();
  }
}
