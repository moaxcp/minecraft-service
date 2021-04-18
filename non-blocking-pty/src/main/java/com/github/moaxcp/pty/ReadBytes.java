package com.github.moaxcp.pty;

import lombok.Value;

@Value
class ReadBytes {
  byte[] bytes;
  int read;
}
