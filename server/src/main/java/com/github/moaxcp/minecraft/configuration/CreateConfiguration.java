package com.github.moaxcp.minecraft.configuration;

import io.micronaut.serde.annotation.Serdeable;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@Serdeable
public class CreateConfiguration {
  @NotBlank
  String serverName;
  int jvmId;
  @Nullable
  JvmSettings jvmSettings;
  int minecraftId;
  boolean bonusChest;
  boolean demo;
  boolean eraseCache;
  boolean noGui;
  @NotBlank
  String universe;
  @NotBlank
  String world;
}
