package com.github.moaxcp.minecraft.server;

import com.github.moaxcp.minecraft.configuration.JvmSettings;
import com.github.moaxcp.minecraft.configuration.MinecraftConfiguration;
import com.github.moaxcp.minecraft.server.cli.StartCommand;
import com.github.moaxcp.minecraft.server.cli.java.JavaArgument;
import com.github.moaxcp.minecraft.server.cli.minecraft.MinecraftArgument;
import lombok.experimental.UtilityClass;

import java.nio.file.Path;

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
        .serverJar(baseDirectory.resolve(server.getMinecraftJar()))
        .javaCommand(server.getJavaBin())
        .minecraftArgument(MinecraftArgument.nogui());
    if(server.getJvmSettings() != null) {
      JvmSettings jvmSettings = server.getJvmSettings();
      if(jvmSettings.getMaxMemory() != null) {
        builder.javaArgument(JavaArgument.maxMemory(server.getJvmSettings().getMaxMemory()));
      }
      if(jvmSettings.getMinMemory() != null) {
        builder.javaArgument(JavaArgument.minMemory(server.getJvmSettings().getMinMemory()));
      }
      if(jvmSettings.getThreadStackSize() != null) {
        builder.javaArgument(JavaArgument.threadStackSize(server.getJvmSettings().getThreadStackSize()));
      }
    }

    return builder.build();
  }
}
