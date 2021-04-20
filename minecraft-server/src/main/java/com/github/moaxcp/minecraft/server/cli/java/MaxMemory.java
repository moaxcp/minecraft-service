package com.github.moaxcp.minecraft.server.cli.java;

public class MaxMemory extends JavaArgument {
  MaxMemory(Memory memory) {
    super(String.format("-Xmx%d%s", memory.getSize(), memory.getUnit()));
  }
}
