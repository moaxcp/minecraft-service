package com.github.moaxcp.minecraft.server;

import com.github.moaxcp.minecraft.server.cli.java.JavaJvm;
import com.github.moaxcp.minecraft.server.cli.java.JvmSettings;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class MinecraftConfiguration {
  String serverName;
  JavaJvm javaJvm;
  JvmSettings jvmSettings;
  MinecraftJar minecraftJar;
  boolean bonusChest;
  boolean demo;
  boolean eraseCache;
  boolean noGui;
  String universe;
  String world;

  /**
   * number of lines of console history to keep.
   */
  int historySize;
}
