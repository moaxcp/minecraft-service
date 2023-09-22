package com.github.moaxcp.pty;

import java.io.InputStream;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;
import lombok.Getter;
import lombok.NonNull;

class Output implements Runnable {

  @Getter
  private final ConcurrentLinkedQueue<ReadBytes> output = new ConcurrentLinkedQueue<>();
  private final InputStream processOutput;
  @Getter
  private volatile boolean running = false;
  @Getter
  private volatile Throwable failure;

  public Output(@NonNull InputStream processOutput) {
    this.processOutput = processOutput;
  }

  @Override
  public void run() {
    running = true;
    while (true) {
      byte[] bytes = new byte[256];
      int read;
      try {
        read = processOutput.read(bytes);
        if (read == -1) {
          break;
        }
      } catch (Throwable e) {
        failure = e;
        break;
      }
      output.add(new ReadBytes(bytes, read));
    }
    running = false;
  }

  public void start() {
    var thread = new Thread(this);
    thread.setName("NonBlockingPty-Output-" + UUID.randomUUID());
    thread.start();
  }
}
