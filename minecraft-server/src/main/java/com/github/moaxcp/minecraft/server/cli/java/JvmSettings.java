package com.github.moaxcp.minecraft.server.cli.java;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class JvmSettings {
  Memory maxMemory;
  Memory minMemory;
  Memory threadStackSize;
}
