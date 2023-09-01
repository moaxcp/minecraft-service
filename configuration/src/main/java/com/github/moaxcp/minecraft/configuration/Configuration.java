package com.github.moaxcp.minecraft.configuration;

import io.micronaut.serde.annotation.Serdeable;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import java.util.List;

@Value
@Builder(toBuilder = true)
@Serdeable
public class Configuration {
    @NonNull
    @Builder.Default
    ServiceConfiguration serviceConfiguration = ServiceConfiguration.builder().build();
    @Singular
    List<JavaJvm> jvms;
    @Singular
    List<MinecraftJar> minecraftJars;
}
