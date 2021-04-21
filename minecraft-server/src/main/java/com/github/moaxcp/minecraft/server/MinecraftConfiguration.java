package com.github.moaxcp.minecraft.server;

import com.github.moaxcp.minecraft.server.cli.java.JavaJvm;
import com.github.moaxcp.minecraft.server.cli.java.JvmSettings;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class MinecraftConfiguration {
  String serverName;
  JavaJvm javaJvm;
  JvmSettings jvmSettings;
  MinecraftJar minecraftJar;
}
