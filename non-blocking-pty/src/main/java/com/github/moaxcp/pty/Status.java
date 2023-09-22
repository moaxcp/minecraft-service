package com.github.moaxcp.pty;

import com.pty4j.WinSize;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Status {
  long pid;
  WinSize winSize;
  Integer exitCode;
  boolean eventLoopRunning;
  Throwable eventLoopFailure;
  boolean inputRunning;
  Throwable inputFailure;
  boolean outputRunning;
  Throwable outputFailure;
  boolean errorRunning;
  Throwable errorFailure;
}
