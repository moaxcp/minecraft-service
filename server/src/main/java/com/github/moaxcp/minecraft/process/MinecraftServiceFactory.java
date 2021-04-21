package com.github.moaxcp.minecraft.process;

import com.github.moaxcp.minecraft.server.MinecraftConfiguration;
import com.github.moaxcp.minecraft.server.MinecraftJar;
import com.github.moaxcp.minecraft.server.MinecraftService;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Property;

import javax.inject.Singleton;
import java.nio.file.Path;

import static com.github.moaxcp.minecraft.server.cli.java.JavaJvm.detectCurrentJvm;

@Factory
public class MinecraftServiceFactory {
  @Singleton
  public MinecraftService minecraftService(@Property(name = "minecraft.process.historyCapacity", defaultValue = "10000") int historyCapacity) {
    var service = new MinecraftService(Path.of("../minecraft").toAbsolutePath(), historyCapacity);

    service.putConfiguration(MinecraftConfiguration.builder()
        .serverName("test-server")
        .javaJvm(detectCurrentJvm())
        .minecraftJar(new MinecraftJar("1.6.5", Path.of("downloads/server-jars/minecraft-server-1.6.5.jar")))
        .build());

    service.selectConfiguration("test-server");

    return service;
  }
}
