package com.github.moaxcp.minecraft.configuration;


import com.github.moaxcp.minecraft.jvm.JvmService;
import com.github.moaxcp.minecraft.server.MinecraftConfiguration;
import com.github.moaxcp.minecraft.server.MinecraftService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import lombok.NonNull;

import java.util.Collection;
import java.util.Optional;

@Controller("/minecraft-configuration")
public class ConfigurationController {

  @NonNull
  private final MinecraftService minecraftService;
  @NonNull
  private final JvmService jvmService;

  public ConfigurationController(MinecraftService minecraftService, JvmService jvmService) {
    this.minecraftService = minecraftService;
    this.jvmService = jvmService;
  }

  @Get("/configuration")
  public Collection<MinecraftConfiguration> configurations() {
    return minecraftService.getConfigurations();
  }

  @Get("/configuration/{configurationName}")
  public Optional<MinecraftConfiguration> getConfiguration(String configurationName) {
    return minecraftService.getConfiguration(configurationName);
  }

  @Post("/configuration")
  public void createConfiguration(CreateConfiguration configuration) {

  }

  @Post("/select-configuration/{configurationName}")
  public void selectConfiguration(@PathVariable String configurationName) {
    minecraftService.selectConfiguration(configurationName);
  }
}
