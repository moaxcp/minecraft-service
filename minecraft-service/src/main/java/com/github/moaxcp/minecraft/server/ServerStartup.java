package com.github.moaxcp.minecraft.server;

import com.github.moaxcp.minecraft.configuration.ConfigurationService;
import com.github.moaxcp.minecraft.configuration.MinecraftConfiguration;
import io.micronaut.context.annotation.Requires;
import io.micronaut.context.env.Environment;
import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.context.event.StartupEvent;
import jakarta.inject.Singleton;
import lombok.AllArgsConstructor;

import java.nio.file.Path;

import static com.github.moaxcp.minecraft.configuration.JavaJvm.detectCurrentJvm;

@Singleton
@Requires(notEnv = Environment.TEST)
@AllArgsConstructor
public class ServerStartup implements ApplicationEventListener<StartupEvent> {

    private final ConfigurationService configurationService;
    private final MinecraftService minecraftService;

    @Override
    public void onApplicationEvent(StartupEvent event) {
        if (configurationService.getConfiguration().getServiceConfiguration().getConfigurations().isEmpty()) {
            configurationService.addConfiguration(MinecraftConfiguration.builder()
                    .serverName("test-server")
                    .javaBin(detectCurrentJvm().getCommand())
                    .minecraftJar(Path.of("downloads/server-jars/minecraft-server-1.6.5.jar"))
                    .build());
            configurationService.selectConfiguration("test-server");
            configurationService.setHistorySize(1000);
        }
        configurationService.getSelectedConfiguration()
                .ifPresent(c -> minecraftService.startProcess());
    }
}
