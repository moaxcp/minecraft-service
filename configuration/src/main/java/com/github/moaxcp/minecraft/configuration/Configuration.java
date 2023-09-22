package com.github.moaxcp.minecraft.configuration;

import io.micronaut.serde.annotation.Serdeable;
import java.nio.file.Path;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

@Value
@Builder(toBuilder = true)
@Serdeable
public class Configuration {
    @NonNull
    Path unixSocketFile;
    @NonNull
    @Builder.Default
    ServiceConfiguration serviceConfiguration = ServiceConfiguration.builder().build();
    @Singular
    List<JavaJvm> jvms;
    @Singular
    List<MinecraftJar> minecraftJars;
}
