package com.github.moaxcp.minecraft.configuration;

import io.micronaut.serde.annotation.Serdeable;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@Serdeable
public class CreateConfiguration {
  private String name;
  private int jvmId;
  private int minecraftId;
}
