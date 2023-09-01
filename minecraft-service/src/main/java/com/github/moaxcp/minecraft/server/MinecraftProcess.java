package com.github.moaxcp.minecraft.server;

import com.github.moaxcp.minecraft.server.cli.StartCommand;
import com.github.moaxcp.pty.NonBlockingPty;
import com.github.moaxcp.pty.Status;
import lombok.Getter;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import static com.github.moaxcp.minecraft.server.MinecraftProcessStatus.*;

public class MinecraftProcess {
  @Getter
  private StartCommand startCommand;
  private final int historyCapacity;
  private NonBlockingPty process;
  private MinecraftProcessStatus status;
  private final List<Byte> history = new ArrayList<>();

  public MinecraftProcess(int historyCapacity, StartCommand startCommand) {
    this.historyCapacity = historyCapacity;
    this.startCommand = startCommand;
    status = CREATED;
  }

  public List<Byte> getHistory() {
    return Collections.unmodifiableList(history);
  }

  public void start() {
    if(process != null && process.isRunning()) {
      throw new IllegalStateException("process is already running " + startCommand);
    }
    try {
      process = new NonBlockingPty(startCommand.getServerDirectory(), startCommand.toCommand());
      process.addOutputListener("server", this::accept);
      process.start();
      status = STARTING;
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }

  public void stop() {
    process.input("stop\n".getBytes(StandardCharsets.UTF_8));
    status = STOPPING;
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

  public MinecraftProcessStatus getStatus() {
    return status;
  }

  public Optional<Status> getProcessStatus() {
    if(process == null) {
      return Optional.empty();
    }
    try {
      return Optional.of(process.status());
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }

  public void destroy() {
    process.stop();
  }
}
