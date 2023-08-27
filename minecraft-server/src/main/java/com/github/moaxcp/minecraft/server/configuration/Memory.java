package com.github.moaxcp.minecraft.server.configuration;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Serdeable
public class Memory {
  @Min(1)
  int size;
  @NotNull
  MemoryUnit unit;

  @JsonCreator
  public static Memory memory(int size, MemoryUnit unit) {
    return new Memory(size, unit);
  }

  public static Memory memory(String memory) {
    if (memory.length() < 2) {
      throw new IllegalArgumentException("memory must have length greater than or equal to 2");
    }
    int size = Integer.parseInt(memory.substring(0, memory.length() - 1));
    var unit = MemoryUnit.parse(memory.substring(memory.length() - 1));
    return memory(size, unit);
  }

  public String toString() {
    return "%d%s".formatted(size, unit);
  }
}
