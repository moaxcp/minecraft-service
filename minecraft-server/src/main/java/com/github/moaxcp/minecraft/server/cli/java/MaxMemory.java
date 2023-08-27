package com.github.moaxcp.minecraft.server.cli.java;

import com.github.moaxcp.minecraft.server.configuration.Memory;

public class MaxMemory extends JavaArgument {
  MaxMemory(Memory memory) {
    super(String.format("-Xmx%d%s", memory.getSize(), memory.getUnit()));
  }
}
