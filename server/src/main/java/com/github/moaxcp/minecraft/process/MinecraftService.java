package com.github.moaxcp.minecraft.process;

import com.github.moaxcp.minecraft.server.cli.StartCommand;
import lombok.Getter;

import javax.inject.Singleton;
import java.io.File;

import static com.github.moaxcp.minecraft.server.cli.minecraft.MinecraftArgument.*;

@Singleton
public class MinecraftService {
  @Getter
  private volatile StartCommand startCommand;

  public MinecraftService() {
    startCommand = StartCommand.builder()
        .serverDirectory(new File("../test-server").getAbsolutePath())
        .serverJar("../downloads/server-jars/minecraft-server-1.6.5.jar")
        .minecraftArgument(nogui())
        .minecraftArgument(universe("universe"))
        .minecraftArgument(world("world"))
        .build();
  }

  public void changeJava(String java) {
    startCommand = startCommand.toBuilder().javaCommand(java).build();
  }
}
