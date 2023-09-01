package com.github.moaxcp.minecraft.configuration;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.micronaut.serde.annotation.Serdeable;
import lombok.*;

import java.util.*;

@Value
@Builder(toBuilder = true)
@Serdeable
public class ServiceConfiguration {

    String selectedServer;

    int historySize;

    @Singular
    Map<String, MinecraftConfiguration> configurations;

    @JsonIgnore
    public Optional<MinecraftConfiguration> getSelectedConfiguration() {
        return Optional.ofNullable(configurations.get(selectedServer));
    }

    public Optional<MinecraftConfiguration> get(String name) {
        return Optional.ofNullable(configurations.get(name));
    }

    public ServiceConfiguration historySize(int historySize) {
        return this.toBuilder().historySize(historySize).build();
    }

    public ServiceConfiguration add(MinecraftConfiguration configuration) {
        return this.toBuilder().configuration(configuration.getServerName(),configuration).build();
    }

    public ServiceConfiguration selectServer(String serverName) {
        if (!configurations.containsKey(serverName)) {
            throw new IllegalArgumentException("serverName \"%s\" is not in configurations".formatted(serverName));
        }
        return this.toBuilder().selectedServer(serverName).build();
    }
}
