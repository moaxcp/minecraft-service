package com.github.moaxcp.minecraft.server.cli;

public abstract class JavaArgument extends AbstractArgument {

  JavaArgument(String key, String value) {
    super(key, value);
  }

  JavaArgument(String arg) {
    super(arg);
  }
}
