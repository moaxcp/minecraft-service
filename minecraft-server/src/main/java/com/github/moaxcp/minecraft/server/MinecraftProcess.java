package com.github.moaxcp.minecraft.server;

import com.github.moaxcp.minecraft.server.cli.StartCommand;
import com.github.moaxcp.pty.NonBlockingPty;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

@RequiredArgsConstructor
public class MinecraftProcess {
  @NonNull
  private final StartCommand startCommand;
  private final int historyCapacity;
  private NonBlockingPty process;
  private final List<Byte> history = new ArrayList<>();

  public List<Byte> getHistory() {
    return Collections.unmodifiableList(history);
  }

  public void start() {
    try {
      process = new NonBlockingPty(startCommand.toCommand());
      process.addOutputListener("server", this::accept);
      process.start();
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }

  public void addListener(String name, Consumer<byte[]> consumer) {
    process.addOutputListener(name, consumer);
  }

  public void removeListener(String name) {
    process.removeOutputListener(name);
  }

  public void input(byte[] bytes) {
    process.input(bytes);
  }

  private void accept(byte[] bytes) {
    for(byte b : bytes) {
      history.add(b);
    }
  }
}
