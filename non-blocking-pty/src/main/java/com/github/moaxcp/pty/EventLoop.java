package com.github.moaxcp.pty;

import com.pty4j.PtyProcess;
import com.pty4j.WinSize;
import lombok.Getter;
import lombok.NonNull;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

class EventLoop implements Runnable {
  @Getter
  private Integer exitCode;
  @Getter
  private boolean running;
  @Getter
  private Throwable failure;
  private final ConcurrentHashMap<String, Consumer<byte[]>> outputListeners = new ConcurrentHashMap<>();
  private final ConcurrentHashMap<String, Consumer<byte[]>> errorListeners = new ConcurrentHashMap<>();
  private final ConcurrentHashMap<String, Consumer<Status>> statusListeners = new ConcurrentHashMap<>();

  private final PtyProcess process;

  private final Input processInput;

  private final Output processOutput;

  private final Output processError;

  private Status status;

  EventLoop(Input processInput, Output processOutput, Output processError, PtyProcess process) {
    this.processInput = processInput;
    this.processOutput = processOutput;
    this.processError = processError;
    this.process = process;
  }

  public void addOutputListeners(Map<String, Consumer<byte[]>> outputListeners) {
    this.outputListeners.putAll(outputListeners);
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

  public void addErrorListeners(Map<String, Consumer<byte[]>> errorListeners) {
    this.errorListeners.putAll(errorListeners);
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

  public void addStatusListeners(Map<String, Consumer<Status>> statusListeners) {
    this.statusListeners.putAll(statusListeners);
  }

  public void addStatusListener(@NonNull String name, @NonNull Consumer<Status> consumer) {
    statusListeners.put(name, consumer);
  }

  public void removeStatusListener(@NonNull String name) {
    statusListeners.remove(name);
  }

  public void removeAllStatusListeners() {
    statusListeners.clear();
  }

  @Override
  public void run() {
    running = true;

    boolean hadOutput = sendOutput();
    boolean hadError = sendError();
    while(process.isAlive()) {
      if (status == null) {
        status = status();
      }
      if(!status.equals(status())) {
        status = status();
        sendStatus();
      }
      if(!(hadOutput || hadError)) {
        try {
          Thread.sleep(20); //check 50 times per second
        } catch (InterruptedException e) {
          failure = e;
          Thread.currentThread().interrupt();
          break;
        }
      }
      hadOutput = sendOutput();
      hadError = sendError();
    }

    running = false;
    process.destroy();
    exitCode = process.exitValue();
  }

  private void sendStatus() {
    for (Consumer<Status> consumer : statusListeners.values()) {
      consumer.accept(status);
    }
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
    if (processError == null) {
      return sendOutput(processOutput, errorListeners.values());
    } else {
      return sendOutput(processError, errorListeners.values());
    }
  }

  public void start() {
    var thread = new Thread(this);
    thread.setName("NonBlockingPty-EventLoop-" + UUID.randomUUID());
    thread.start();
  }

  public Status status() {
    WinSize winSize = null;
    try {
      winSize = process.getWinSize();
    } catch (IOException e) {
      //will be null
    }
    return Status.builder()
        .pid(process.pid())
        .winSize(winSize)
        .exitCode(getExitCode())
        .eventLoopRunning(isRunning())
        .eventLoopFailure(getFailure())
        .inputRunning(processInput.isRunning())
        .inputFailure(processInput.getFailure())
        .outputRunning(processOutput.isRunning())
        .outputFailure(processOutput.getFailure())
        .errorRunning(processError.isRunning())
        .errorFailure(processError.getFailure())
        .build();
  }
}
