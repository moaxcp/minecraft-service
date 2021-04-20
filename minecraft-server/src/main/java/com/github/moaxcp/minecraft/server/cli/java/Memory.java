package com.github.moaxcp.minecraft.server.cli.java;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Memory {
  int size;
  MemoryUnit unit;
}
