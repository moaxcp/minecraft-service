package com.github.moaxcp.minecraft.server.cli.java;

public enum MemoryUnit {
  K("k"),
  M("m"),
  G("g");

  private final String suffix;

  MemoryUnit(String suffix) {
    this.suffix = suffix;
  }

  public String toString() {
    return suffix;
  }
}
