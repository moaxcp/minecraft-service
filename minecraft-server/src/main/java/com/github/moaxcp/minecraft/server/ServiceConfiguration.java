package com.github.moaxcp.minecraft.server;

import com.github.moaxcp.minecraft.server.configuration.MinecraftConfiguration;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Serdeable
public class ServiceConfiguration {
    @Setter
    @Getter
    private String selectedServer;

    @Setter
    @Getter
    private int historySize;

    private final Map<String, MinecraftConfiguration> configurations = new HashMap<>();

    public Optional<MinecraftConfiguration> getSelectedConfiguration() {
        return Optional.ofNullable(configurations.get(selectedServer));
    }

    public Optional<MinecraftConfiguration> get(String name) {
        return Optional.ofNullable(configurations.get(name));
    }

    public Set<MinecraftConfiguration> configurations() {
        return Collections.unmodifiableSet(new HashSet<>(configurations.values()));
    }

    public void add(MinecraftConfiguration configuration) {
        configurations.put(configuration.getServerName(), configuration);
    }

    public void selectServer(String serverName) {
        if (!configurations.containsKey(serverName)) {
            throw new IllegalArgumentException("serverName \"%s\" is not in configurations".formatted(serverName));
        }
        selectedServer = serverName;
    }
}
