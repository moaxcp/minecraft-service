package com.github.moaxcp.minecraft;


import com.github.moaxcp.minecraft.server.MinecraftConfiguration;
import com.github.moaxcp.minecraft.server.MinecraftService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;

import java.util.Collection;
import java.util.Optional;

@Controller("/minecraft-configuration")
public class ConfigurationController {

  private MinecraftService minecraftService;

  public ConfigurationController(MinecraftService minecraftService) {
    this.minecraftService = minecraftService;
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
  public void createConfiguration(MinecraftConfiguration configuration) {
    minecraftService.putConfiguration(configuration);
  }

  @Post("/select-configuration/{configurationName}")
  public void selectConfiguration(@PathVariable String configurationName) {
    minecraftService.selectConfiguration(configurationName);
  }
}
