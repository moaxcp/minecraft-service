package com.github.moaxcp.minecraft.server;

import com.github.moaxcp.minecraft.server.cli.StartCommand;

import java.nio.file.Path;
import java.util.*;

/**
 * manages business rules for server settings and the specific server running.
 *
 * One one server can run at a time
 *
 */
public class MinecraftService {
  private Map<String, MinecraftConfiguration> serverConfigs = new HashMap<>();
  private MinecraftConfiguration selectedConfiguration;
  private MinecraftConfiguration runningConfiguration;
  private volatile MinecraftProcess process;
  private Path baseDirectory;
  private volatile int historySize;

  /**
   *
   * @param historySize lines of console history to keep
   */
  public MinecraftService(Path baseDirectory, int historySize) {
    this.baseDirectory = baseDirectory;
    this.historySize = historySize;
  }

  public synchronized void putConfiguration(MinecraftConfiguration serverConfig) {
    if(process != null && runningConfiguration != null && runningConfiguration.getServerName().equals(serverConfig.getServerName()) && process.isRunning()) {
      throw new IllegalArgumentException("cannot modify config of running server. Stop the server before configuring it.");
    }
    serverConfigs.put(serverConfig.getServerName(), serverConfig);
  }

  public synchronized void selectConfiguration(String configurationName) {
    selectedConfiguration = serverConfigs.get(configurationName);
  }

  public synchronized void startProcess() {
    if(process != null && process.isRunning()) {
      throw new IllegalStateException("process must first be stopped.");
    }
    process = new MinecraftProcess(historySize, Conventions.toStartCommand(baseDirectory, selectedConfiguration));
    process.start();
    runningConfiguration = selectedConfiguration;
  }

  public synchronized void stopProcess() {
    if(process == null) {
      return;
    }
    process.stop();
    runningConfiguration = null;
  }

  public synchronized Optional<MinecraftConfiguration> getRunningConfiguration() {
    return Optional.ofNullable(runningConfiguration);
  }

  public synchronized Optional<StartCommand> getRunningStartCommand() {
    if(process == null) {
      return Optional.empty();
    }
    return Optional.of(process.getStartCommand());
  }

  public synchronized Collection<MinecraftConfiguration> getConfigurations() {
    return Collections.unmodifiableCollection(serverConfigs.values());
  }

  public synchronized Optional<MinecraftConfiguration> getConfiguration(String configurationName) {
    return Optional.ofNullable(serverConfigs.get(configurationName));
  }

  public synchronized void destroy() {
    if(process == null) {
      return;
    }
    process.destroy();
    runningConfiguration = null;
  }

  public synchronized Optional<MinecraftServerStatus> getServerStatus() {
    if(process == null) {
      return Optional.empty();
    }
    return Optional.of(MinecraftServerStatus.builder()
        .runningConfiguration(runningConfiguration)
        .selectedConfiguration(selectedConfiguration)
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
}
