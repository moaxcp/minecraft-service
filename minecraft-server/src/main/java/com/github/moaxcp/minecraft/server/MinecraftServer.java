package com.github.moaxcp.minecraft.server;

import com.github.moaxcp.minecraft.server.cli.StartCommand;
import com.github.moaxcp.minecraft.server.cli.java.JavaJvm;
import com.github.moaxcp.minecraft.server.cli.java.JvmSettings;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class MinecraftServer {
  String name;
  JavaJvm javaJvm;
  JvmSettings jvmSettings;
  String version;
  String universe;
  String world;

  public StartCommand startCommand() {
    return Conventions.toStartCommand(this);
  }
}
