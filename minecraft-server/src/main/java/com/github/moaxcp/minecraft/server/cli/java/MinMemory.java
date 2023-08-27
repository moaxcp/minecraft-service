package com.github.moaxcp.minecraft.server.cli.java;

import com.github.moaxcp.minecraft.server.configuration.Memory;

public class MinMemory extends JavaArgument {
  MinMemory(Memory memory) {
    super(String.format("-Xms%d%s", memory.getSize(), memory.getUnit()));
  }
}
