package com.github.moaxcp.minecraft.server.cli.java;

public class MinMemory extends JavaArgument {
  MinMemory(Memory memory) {
    super(String.format("-Xms%d%s", memory.getSize(), memory.getUnit()));
  }
}
