package com.github.moaxcp.minecraft.server.configuration;

import io.micronaut.serde.annotation.Serdeable;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@Serdeable
public class MinecraftConfiguration {
  @NotBlank
  String serverName;
  @NotNull
  JavaJvm javaJvm;
  @Nullable
  JvmSettings jvmSettings;
  @NotNull
  MinecraftJar minecraftJar;
  boolean bonusChest;
  boolean demo;
  boolean eraseCache;
  boolean noGui = true;
  @NotBlank
  String universe;
  @NotBlank
  String world;

  /**
   * number of lines of console history to keep.
   */
  @Min(0)
  int historySize;
}
