package com.github.moaxcp.minecraft.server.cli.java;

import com.github.moaxcp.minecraft.server.cli.AbstractArgument;

public abstract class JavaArgument extends AbstractArgument {

  public static MaxMemory maxMemory(Memory memory) {
    return new MaxMemory(memory);
  }

  public static MinMemory minMemory(Memory memory) {
    return new MinMemory(memory);
  }

  public static ThreadStackSize threadStackSize(Memory memory) {
    return new ThreadStackSize(memory);
  }

  JavaArgument(String key, String value) {
    super(key, value);
  }

  JavaArgument(String arg) {
    super(arg);
  }
}
