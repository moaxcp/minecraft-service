package com.github.moaxcp.minecraft.server.cli.java;

import com.github.moaxcp.minecraft.configuration.Memory;

public class ThreadStackSize extends JavaArgument {
  ThreadStackSize(Memory memory) {
    super(String.format("-Xss%d%s", memory.getSize(), memory.getUnit()));
  }
}
