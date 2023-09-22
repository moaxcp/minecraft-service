package com.github.moaxcp.pty;

import com.pty4j.PtyProcess;
import lombok.Getter;
import lombok.NonNull;

import java.io.OutputStream;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;

class Input implements Runnable {
  private final PtyProcess process;
  @Getter
  private final ConcurrentLinkedQueue<byte[]> input = new ConcurrentLinkedQueue<>();
  private final OutputStream output;
  @Getter
  private volatile boolean running = false;
  @Getter
  private volatile Throwable failure;

  public Input(@NonNull PtyProcess process) {
    this.process = process;
    this.output = process.getOutputStream();
  }

  @Override
  public void run() {
    running = true;
    while (process.isAlive()) {
      byte[] bytes = input.poll();
      if (bytes == null) {
        try {
          Thread.sleep(20);
          continue;
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
          failure = e;
          break;
        }
      }
    }
    running = false;
  }

  public void start() {
    var thread = new Thread(this);
    thread.setName("NonBlockingPty-Input-" + UUID.randomUUID());
    thread.start();
  }
}
