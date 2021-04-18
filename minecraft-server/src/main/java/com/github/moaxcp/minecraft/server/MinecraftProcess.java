package com.github.moaxcp.minecraft.server;

import com.github.moaxcp.minecraft.server.cli.StartCommand;
import com.github.moaxcp.pty.NonBlockingPty;
import com.github.moaxcp.pty.Status;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@RequiredArgsConstructor
public class MinecraftProcess {
  @Getter
  private StartCommand startCommand = StartCommand.builder().build();
  private final int historyCapacity;
  private NonBlockingPty process;
  private final List<Byte> history = new ArrayList<>();

  public List<Byte> getHistory() {
    return Collections.unmodifiableList(history);
  }

  public void setStartCommand(StartCommand startCommand) {
    this.startCommand = startCommand;
  }

  public void start() {
    if(process != null && process.isRunning()) {
      throw new IllegalStateException("process is already running " + startCommand);
    }
    try {
      process = new NonBlockingPty(startCommand.toCommand());
      process.addOutputListener("server", this::accept);
      process.start();
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }

  public boolean isRunning() {
    return process.isRunning();
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

  public Optional<Status> getStatus() {
    if(process == null) {
      return Optional.empty();
    }
    try {
      return Optional.of(process.status());
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }
}
