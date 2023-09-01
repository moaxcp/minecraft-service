package com.github.moaxcp.minecraft.server.cli;

import java.util.List;

public abstract class AbstractArgument implements Argument {
  private final List<String> arguments;

  public AbstractArgument(String... arguments) {
    this.arguments = List.of(arguments);
  }

  @Override
  public final List<String> getArguments() {
    return arguments;
  }
}
