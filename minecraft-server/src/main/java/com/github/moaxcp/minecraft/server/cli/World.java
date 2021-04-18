package com.github.moaxcp.minecraft.server.cli;

import lombok.NonNull;

public class World extends MinecraftArgument {
  public World(@NonNull String world) {
    super("--world", world);
  }
}
