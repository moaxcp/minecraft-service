package com.github.moaxcp.minecraft.server;

import lombok.Value;

import java.nio.file.Path;

@Value
public class MinecraftJar {
  String version;
  Path location;
}
