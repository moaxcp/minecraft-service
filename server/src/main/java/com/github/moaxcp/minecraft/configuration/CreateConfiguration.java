package com.github.moaxcp.minecraft.configuration;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class CreateConfiguration {
  private String name;
  private int jvmId;
  private int minecraftId;
}
