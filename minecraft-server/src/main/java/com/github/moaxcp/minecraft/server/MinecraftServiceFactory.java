package com.github.moaxcp.minecraft.server;

import com.github.moaxcp.minecraft.configuration.ConfigurationService;
import io.micronaut.context.annotation.Factory;
import io.micronaut.serde.ObjectMapper;
import jakarta.inject.Singleton;

import java.nio.file.Path;

@Factory
public class MinecraftServiceFactory {
  @Singleton
  public MinecraftService minecraftService(ObjectMapper mapper, ConfigurationService configurationService) {
    var service = new MinecraftService(mapper, configurationService);

    return service;
  }
}
