package com.github.moaxcp.minecraft.server;

import com.github.moaxcp.minecraft.server.configuration.MinecraftConfiguration;
import com.github.moaxcp.minecraft.server.configuration.MinecraftJar;
import io.micronaut.context.annotation.Factory;
import io.micronaut.serde.ObjectMapper;
import jakarta.inject.Singleton;

import java.nio.file.Path;

import static com.github.moaxcp.minecraft.server.configuration.JavaJvm.detectCurrentJvm;

@Factory
public class MinecraftServiceFactory {
  @Singleton
  public MinecraftService minecraftService(ObjectMapper mapper) {
    var service = new MinecraftService(mapper, Path.of("../minecraft").toAbsolutePath());

    service.addConfiguration(MinecraftConfiguration.builder()
        .serverName("test-server")
        .javaJvm(detectCurrentJvm())
        .minecraftJar(new MinecraftJar("1.6.5", Path.of("downloads/server-jars/minecraft-server-1.6.5.jar")))
        .build());

    service.selectConfiguration("test-server");

    return service;
  }
}
