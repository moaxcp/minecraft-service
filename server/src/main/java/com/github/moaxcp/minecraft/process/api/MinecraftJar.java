package com.github.moaxcp.minecraft.process.api;

import lombok.Data;

import java.io.File;

@Data
public class MinecraftJar {
  private String version;
  private File location;
}
