package com.github.moaxcp.pty;

import com.pty4j.PtyProcess;
import com.pty4j.PtyProcessBuilder;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class NonBlockingPty implements AutoCloseable {
  private final PtyProcessBuilder builder;
  boolean redirectError;
  private PtyProcess process;
  private Input input;
  private Output output;
  private Output error;
  private EventLoop eventLoop;
  private final Map<String, Consumer<byte[]>> outputListeners = new HashMap<>();
  private final Map<String, Consumer<byte[]>> errorListeners = new HashMap<>();
  private final Map<String, Consumer<Status>> statusListeners = new HashMap<>();
  private final List<Plugin> plugins = new ArrayList<>();

  public NonBlockingPty(Path commandDirectory, String... command) throws IOException {
    this(commandDirectory, true, command);
  }

  public NonBlockingPty(Path commandDirectory, boolean redirectError, String... command) {
    builder = new PtyProcessBuilder(command).setDirectory(commandDirectory.normalize().toString());
  }

  public NonBlockingPty(Path commandDirectory, List<String> toCommand) throws IOException {
    this(commandDirectory, toCommand.toArray(new String[]{}));
  }

  public void register(Plugin plugin) {
    plugin.input(this::input);
    addOutputListener(plugin.getName(), plugin::output);
    addErrorListener(plugin.getName(), plugin::error);
    addStatusListener(plugin.getName(), plugin::status);
  }

  public void start() throws IOException {
    process = builder.start();
    input = new Input(process);
    output = new Output(process.getInputStream());

    input.start();
    output.start();
    if(!redirectError) {
      error = new Output(process.getErrorStream());
      error.start();
    } else {
      error = null;
    }
    eventLoop = new EventLoop(input, output, error, process);
    eventLoop.addOutputListeners(outputListeners);
    eventLoop.addErrorListeners(errorListeners);
    eventLoop.addStatusListeners(statusListeners);
    eventLoop.start();
  }

  public void addOutputListener(String name, Consumer<byte[]> listener) {
    if(eventLoop == null) {
      outputListeners.put(name, listener);
    } else {
      eventLoop.addOutputListener(name, listener);
    }
  }

  public void removeOutputListener(String name) {
    if (eventLoop == null) {
      outputListeners.remove(name);
    } else {
      eventLoop.removeOutputListener(name);
    }
  }

  public void removeAllOutputListeners() {
    if (eventLoop == null) {
      outputListeners.clear();
    } else {
      eventLoop.removeAllOutputListeners();
    }
  }

  public void addErrorListener(String name, Consumer<byte[]> listener) {
    if (eventLoop == null) {
      errorListeners.put(name, listener);
    } else {
      eventLoop.addErrorListener(name, listener);
    }
  }

  public void removeErrorListener(String name) {
    if (eventLoop == null) {
      errorListeners.remove(name);
    } else {
      eventLoop.removeErrorListener(name);
    }
  }

  public void removeAllErrorListeners() {
    if (eventLoop == null) {
      errorListeners.clear();
    } else {
      eventLoop.removeAllErrorListeners();
    }
  }

  public void addStatusListener(String name, Consumer<Status> listener) {
    if (eventLoop == null) {
      statusListeners.put(name, listener);
    } else {
      eventLoop.addStatusListener(name, listener);
    }
  }

  public void removeStatusListener(String name) {
    if (eventLoop == null) {
      statusListeners.remove(name);
    } else {
      eventLoop.removeStatusListener(name);
    }
  }

  public void removeAllStatusListeners() {
    if (eventLoop == null) {
      statusListeners.clear();
    } else {
      eventLoop.removeAllStatusListeners();
    }
  }

  public Status status() throws IOException {
    return eventLoop.status();
  }

  public boolean isRunning() {
    return eventLoop.isRunning() || input.isRunning() || output.isRunning() || error.isRunning();
  }

  public void stop() {
    process.destroy();
  }

  public void waitFor(long timeout, TimeUnit unit) throws InterruptedException {
    process.waitFor(timeout, unit);
  }

  public void input(byte[] bytes) {
    input.getInput().add(bytes);
  }

  @Override
  public void close() {
    stop();
  }
}
