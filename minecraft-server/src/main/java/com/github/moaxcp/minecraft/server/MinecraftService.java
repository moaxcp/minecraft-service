package com.github.moaxcp.minecraft.server;

import com.github.moaxcp.minecraft.configuration.ConfigurationService;
import com.github.moaxcp.minecraft.configuration.MinecraftConfiguration;
import com.github.moaxcp.minecraft.server.cli.StartCommand;

import java.util.Optional;

/**
 * manages business rules for server settings and the specific server running.
 *
 * One one server can run at a time
 *
 */
public class MinecraftService {

  private volatile MinecraftProcess process;
  private MinecraftConfiguration runningServer;
  private final ConfigurationService configurationService;

  public MinecraftService(ConfigurationService configurationService) {
    this.configurationService = configurationService;
  }

  public synchronized Optional<MinecraftConfiguration> getRunningConfiguration() {
    return Optional.ofNullable(runningServer);
  }

  public synchronized Optional<StartCommand> getRunningStartCommand() {
    if(process == null) {
      return Optional.empty();
    }
    return Optional.of(process.getStartCommand());
  }

  public synchronized Optional<MinecraftServerStatus> getServerStatus() {
    if(process == null) {
      return Optional.empty();
    }
    return Optional.of(MinecraftServerStatus.builder()
            .runningConfiguration(runningServer)
            .selectedConfiguration(configurationService.getSelectedConfiguration().orElse(null))
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
    var startConfiguration = configurationService.getSelectedConfiguration().orElseThrow(() -> new IllegalStateException("startConfiguration must be selected"));
    process = new MinecraftProcess(startConfiguration.getHistorySize(), Conventions.toStartCommand(configurationService.getBaseDirectory(), startConfiguration));
    process.start();
    runningServer = startConfiguration;
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
