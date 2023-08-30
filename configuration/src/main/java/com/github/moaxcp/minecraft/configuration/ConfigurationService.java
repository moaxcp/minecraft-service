package com.github.moaxcp.minecraft.configuration;

import io.micronaut.context.annotation.Property;
import io.micronaut.serde.ObjectMapper;
import jakarta.inject.Singleton;
import lombok.Getter;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.Optional;

@Singleton
public class ConfigurationService {
    @Getter
    private final Path baseDirectory;
    private final Path configurationPath;
    private final ObjectMapper mapper;
    private Configuration configuration;
    public ConfigurationService(ObjectMapper mapper, @Property(name = "base-directory") Path baseDirectory) {
        this.mapper = mapper;
        this.baseDirectory = baseDirectory;
        configurationPath = baseDirectory.resolve("configuration.json");
        try (var in = Files.newInputStream(configurationPath)) {
            configuration = mapper.readValue(in, Configuration.class);
        } catch (NoSuchFileException e) {
            configuration = Configuration.builder().build();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public synchronized void setHistorySize(int historySize) {
        configuration = configuration.toBuilder()
                .serviceConfiguration(configuration.getServiceConfiguration()
                        .historySize(historySize))
                .build();
        save();
    }

    private synchronized void save() {
        try {
            if (Files.notExists(configurationPath)) {
                Files.createDirectories(configurationPath.getParent());
                Files.createFile(configurationPath);
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        try (var out = Files.newOutputStream(configurationPath)) {
            mapper.writeValue(out, configuration);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public synchronized void addConfiguration(MinecraftConfiguration server) {
        configuration = configuration.toBuilder().serviceConfiguration(configuration.getServiceConfiguration()
                .add(server))
                .build();
        save();
    }

    public synchronized void selectConfiguration(String configurationName) {
        configuration = configuration.toBuilder().serviceConfiguration(configuration.getServiceConfiguration()
                .selectServer(configurationName)).build();
        save();
    }

    public Optional<MinecraftConfiguration> getMinecraftConfiguration(String serverName) {
        return configuration.getServiceConfiguration().get(serverName);
    }

    public Optional<MinecraftConfiguration> getSelectedConfiguration() {
        return configuration.getServiceConfiguration().getSelectedConfiguration();
    }

    public void addMinecraftJar(MinecraftJar jar) {
        configuration = configuration.toBuilder().minecraftJar(jar).build();
    }
}
