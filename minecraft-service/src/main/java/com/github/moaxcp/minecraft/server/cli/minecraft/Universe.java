package com.github.moaxcp.minecraft.server.cli.minecraft;

import lombok.NonNull;

import java.nio.file.Path;

public class Universe extends MinecraftArgument {
  public Universe(@NonNull Path universe) {
    super("--universe", universe.normalize().toAbsolutePath().toString());
  }
}
