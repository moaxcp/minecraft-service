package com.github.moaxcp.minecraft.configuration;

import io.micronaut.serde.annotation.Serdeable;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

import java.nio.file.Path;

@Value
@Builder
@Serdeable
public class MinecraftConfiguration {
  @NotBlank
  String serverName;
  @NotNull
  Path javaBin;
  @Nullable
  JvmSettings jvmSettings;
  @NotNull
  Path minecraftJar;
  boolean bonusChest;
  boolean demo;
  boolean eraseCache;
  boolean noGui = true;
  @NotBlank
  String universe;
  @NotBlank
  String world;
}
