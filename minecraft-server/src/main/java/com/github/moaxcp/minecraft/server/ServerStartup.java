package com.github.moaxcp.minecraft.server;

import com.github.moaxcp.minecraft.configuration.ConfigurationService;
import io.micronaut.context.annotation.Requires;
import io.micronaut.context.env.Environment;
import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.context.event.StartupEvent;
import jakarta.inject.Singleton;
import lombok.AllArgsConstructor;

@Singleton
@Requires(notEnv = Environment.TEST)
@AllArgsConstructor
public class ServerStartup implements ApplicationEventListener<StartupEvent> {

    private final ConfigurationService configurationService;
    private final MinecraftService minecraftService;
    @Override
    public void onApplicationEvent(StartupEvent event) {
        configurationService.getSelectedConfiguration()
                .ifPresent(c -> minecraftService.startProcess());
    }
}
