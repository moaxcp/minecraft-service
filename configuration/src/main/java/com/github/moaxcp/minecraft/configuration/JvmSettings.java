package com.github.moaxcp.minecraft.configuration;

import io.micronaut.serde.annotation.Serdeable;
import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@Serdeable
public class JvmSettings {
  @Nullable
  Memory maxMemory;
  @Nullable
  Memory minMemory;
  @Nullable
  Memory threadStackSize;
}
