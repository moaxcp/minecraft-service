package com.github.moaxcp.pty;

import com.pty4j.PtyProcess;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import lombok.Getter;
import lombok.NonNull;

class EventLoop implements Runnable {
  @Getter
  private Integer result;
  @Getter
  private boolean running;
  @Getter
  private Throwable throwable;
  private final ConcurrentHashMap<String, Consumer<byte[]>> outputListeners = new ConcurrentHashMap<>();
  private final ConcurrentHashMap<String, Consumer<byte[]>> errorListeners = new ConcurrentHashMap<>();

  private final PtyProcess process;

  private final Output processOutput;
  private final Output processError;

  EventLoop(Output processOutput, Output processError, PtyProcess process) {
    this.processOutput = processOutput;
    this.processError = processError;
    this.process = process;
  }

  public void addOutputListener(@NonNull String name, @NonNull Consumer<byte[]> consumer) {
    outputListeners.put(name, consumer);
  }

  public void removeOutputListener(@NonNull String name) {
    outputListeners.remove(name);
  }

  public void removeAllOutputListeners() {
    outputListeners.clear();
  }

  public void addErrorListener(@NonNull String name, @NonNull Consumer<byte[]> consumer) {
    errorListeners.put(name, consumer);
  }

  public void removeErrorListener(@NonNull String name) {
    errorListeners.remove(name);
  }

  public void removeAllErrorListeners() {
    errorListeners.clear();
  }

  @Override
  public void run() {
    running = true;

    boolean hadOutput = sendOutput();
    boolean hadError = sendError();
    while(hadOutput || hadError || process.isAlive()) {
      if(!(hadOutput || hadError)) {
        try {
          Thread.sleep(10);
        } catch (InterruptedException e) {
          throwable = e;
          Thread.currentThread().interrupt();
          break;
        }
      }
      hadOutput = sendOutput();
      hadError = sendError();
    }

    running = false;
    result = process.exitValue();
  }

  private boolean sendOutput() {
    return sendOutput(processOutput, outputListeners.values());
  }

  private boolean sendOutput(Output output, Collection<Consumer<byte[]>> consumers) {
    boolean hadOutput = false;
    while(!output.getOutput().isEmpty()) {
      ReadBytes bytes = output.getOutput().poll();
      if (bytes != null) {
        hadOutput = true;
      }
      sendBytes(bytes, consumers);
    }
    return hadOutput;
  }

  private void sendBytes(ReadBytes bytes, Collection<Consumer<byte[]>> consumers) {
    for (Consumer<byte[]> consumer : consumers) {
      try {
        consumer.accept(Arrays.copyOfRange(bytes.getBytes(), 0, bytes.getRead()));
      } catch(Exception e) {
        //todo handle exception. shutdown process? skip consumer?
      }
    }
  }

  private boolean sendError() {
    return sendOutput(processError, errorListeners.values());
  }

  public void addOutputListeners(Map<String, Consumer<byte[]>> outputListeners) {
    this.outputListeners.putAll(outputListeners);
  }
}
