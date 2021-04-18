package com.github.moaxcp.minecraft.process;

import com.github.moaxcp.minecraft.server.MinecraftProcess;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Property;

import javax.inject.Singleton;

@Factory
public class MinecraftProcessFactory {
  @Singleton
  public MinecraftProcess minecraftProcess(@Property(name = "minecraft.process.historyCapacity", defaultValue = "10000") int historyCapacity) {
    return new MinecraftProcess(historyCapacity);
  }
}
