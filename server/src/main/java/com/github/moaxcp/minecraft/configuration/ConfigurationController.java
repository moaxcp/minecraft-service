package com.github.moaxcp.minecraft.configuration;


import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.util.Optional;

@Secured(SecurityRule.IS_AUTHENTICATED)
@Controller("/minecraft-configuration")
@AllArgsConstructor
public class ConfigurationController {

  @NonNull
  private final ConfigurationService configurationService;

  @Get("/configuration")
  public Configuration configurations() {
    return configurationService.getConfiguration();
  }

  @Get("/configuration/{serverName}")
  public Optional<MinecraftConfiguration> getConfiguration(String serverName) {
    return configurationService.getMinecraftConfiguration(serverName);
  }

  @Post("/configuration")
  public void createConfiguration(CreateConfiguration configuration) {

  }

  @Post("/select-configuration/{configurationName}")
  public void selectConfiguration(@PathVariable String configurationName) {
    configurationService.selectConfiguration(configurationName);
  }
}
