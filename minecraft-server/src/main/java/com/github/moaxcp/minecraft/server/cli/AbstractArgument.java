package com.github.moaxcp.minecraft.server.cli;

import java.util.List;

abstract class AbstractArgument implements Argument {
  private final List<String> command;

  public AbstractArgument(String... arguments) {
    command = List.of(arguments);
  }

  @Override
  public List<String> forCommandLine() {
    return command;
  }
}
