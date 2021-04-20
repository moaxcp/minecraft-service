package com.github.moaxcp.minecraft.server.cli.minecraft;

import lombok.NonNull;

public class Universe extends MinecraftArgument {
  public Universe(@NonNull String universe) {
    super("--universe", universe);
  }
}
