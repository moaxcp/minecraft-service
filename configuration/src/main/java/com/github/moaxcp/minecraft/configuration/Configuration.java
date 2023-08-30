package com.github.moaxcp.minecraft.configuration;

import io.micronaut.serde.annotation.Serdeable;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;

import java.util.List;

@Value
@Builder(toBuilder = true)
@Serdeable
public class Configuration {
    ServiceConfiguration serviceConfiguration;
    @Singular
    List<MinecraftJar> minecraftJars;
}
