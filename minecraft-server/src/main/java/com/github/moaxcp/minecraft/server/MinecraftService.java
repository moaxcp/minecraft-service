package com.github.moaxcp.minecraft.server;

import com.github.moaxcp.minecraft.server.configuration.Conventions;
import com.github.moaxcp.minecraft.server.configuration.MinecraftConfiguration;
import io.micronaut.serde.ObjectMapper;
import com.github.moaxcp.minecraft.server.cli.StartCommand;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * manages business rules for server settings and the specific server running.
 *
 * One one server can run at a time
 *
 */
public class MinecraftService {

  private volatile MinecraftProcess process;
  private final Path baseDirectory;
  private final Path configurationPath;
  private String runningServer;
  private ServiceConfiguration configuration;
  private final ObjectMapper mapper;

  public MinecraftService(ObjectMapper mapper, Path baseDirectory) {
    this.mapper = mapper;
    this.baseDirectory = baseDirectory;
    configurationPath = baseDirectory.resolve("configuration.json");
    try (var in = Files.newInputStream(configurationPath)) {
      configuration = mapper.readValue(in, ServiceConfiguration.class);
    } catch (FileNotFoundException e) {
      configuration = new ServiceConfiguration();
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }

  public synchronized void setHistorySize(int historySize) {
    configuration.setHistorySize(historySize);
    save();
  }

  private synchronized void save() {
    try (var out = Files.newOutputStream(configurationPath)) {
      mapper.writeValue(out, configuration);
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }

  public synchronized void addConfiguration(MinecraftConfiguration serverConfig) {
    if(process != null && runningServer != null && runningServer.equals(serverConfig.getServerName()) && process.isRunning()) {
      throw new IllegalArgumentException("cannot modify config of running server. Stop the server before configuring it.");
    }
    configuration.add(serverConfig);
    save();
  }

  public synchronized void selectConfiguration(String configurationName) {
    configuration.selectServer(configurationName);
    save();
  }

  public synchronized Optional<MinecraftConfiguration> getRunningConfiguration() {
    return configuration.get(runningServer);
  }

  public synchronized Optional<StartCommand> getRunningStartCommand() {
    if(process == null) {
      return Optional.empty();
    }
    return Optional.of(process.getStartCommand());
  }

  public synchronized Set<MinecraftConfiguration> getConfigurations() {
    return configuration.configurations();
  }

  public synchronized Optional<MinecraftConfiguration> getConfiguration(String configurationName) {
    return configuration.get(configurationName);
  }

  public synchronized Optional<MinecraftServerStatus> getServerStatus() {
    if(process == null) {
      return Optional.empty();
    }
    return Optional.of(MinecraftServerStatus.builder()
            .runningConfiguration(getRunningConfiguration().orElse(null))
            .selectedConfiguration(configuration.getSelectedConfiguration().orElse(null))
            .startCommand(process.getStartCommand())
            .processStatus(process.getProcessStatus())
            .minecraftProcessStatus(process.getStatus())
            .build());
  }

  public synchronized Optional<String> getHistory() {
    if(process == null) {
      return Optional.empty();
    }
    byte[] bytes = new byte[process.getHistory().size()];
    for(int i = 0; i < process.getHistory().size(); i++) {
      bytes[i] = process.getHistory().get(i);
    }
    return Optional.of(new String(bytes));
  }

  public synchronized void startProcess() {
    if(process != null && process.isRunning()) {
      throw new IllegalStateException("process must first be stopped.");
    }
    var startConfiguration = configuration.getSelectedConfiguration().orElseThrow(() -> new IllegalStateException("startConfiguration must be selected"));
    process = new MinecraftProcess(startConfiguration.getHistorySize(), Conventions.toStartCommand(baseDirectory, startConfiguration));
    process.start();
    runningServer = startConfiguration.getServerName();
  }

  public synchronized void stopProcess() {
    if(process == null) {
      return;
    }
    process.stop();
    runningServer = null;
  }

  public synchronized void destroy() {
    if(process == null) {
      return;
    }
    process.destroy();
    runningServer = null;
  }
}
