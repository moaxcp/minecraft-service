package com.github.moaxcp.minecraft.server.configuration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.micronaut.serde.annotation.Serdeable;
import lombok.NonNull;

import java.util.Arrays;
import java.util.Map;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@Serdeable
public enum MemoryUnit {
  K("k"),
  M("m"),
  G("g");

  private final String suffix;

  private static final Map<String, MemoryUnit> mappings = Arrays.stream(values()).collect(toMap(MemoryUnit::toString, identity()));

  MemoryUnit(String suffix) {
    this.suffix = suffix;
  }

  @JsonCreator
  public static MemoryUnit parse(@NonNull String s) {
    return mappings.get(s);
  }

  @JsonValue
  public String toString() {
    return suffix;
  }
}
