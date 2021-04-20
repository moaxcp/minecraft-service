package com.github.moaxcp.minecraft.process;

import com.github.moaxcp.minecraft.server.MinecraftProcess;
import com.github.moaxcp.minecraft.server.MinecraftReport;
import com.github.moaxcp.minecraft.server.cli.StartCommand;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import lombok.NonNull;

import java.io.File;

import static com.github.moaxcp.minecraft.server.cli.minecraft.MinecraftArgument.*;

@Controller("/minecraft-process")
public class MinecraftProcessController {
  @NonNull
  private final MinecraftProcess minecraftProcess;
  private StartCommand startCommand;

  public MinecraftProcessController(@NonNull MinecraftProcess minecraftProcess) {
    this.minecraftProcess = minecraftProcess;
    startCommand = StartCommand.builder()
        .serverDirectory(new File("../test-server").getAbsolutePath())
        .serverJar("../downloads/server-jars/minecraft-server-1.6.5.jar")
        .minecraftArgument(nogui())
        .minecraftArgument(universe("universe"))
        .minecraftArgument(world("world"))
        .build();
    minecraftProcess.setStartCommand(startCommand);
  }

  @Get("/start-command")
  public StartCommand startCommand() {
    return startCommand;
  }

  @Post("/start")
  public void start() {
    minecraftProcess.start();
  }

  @Post("/stop")
  public void stop() {
    minecraftProcess.stop();
  }

  /**
   * Calls the destroy method on the pty process for minecraft. This is supposed to gracefully shutdown the server but
   * sending "stop" in the console is preferred. Use {@link #stop()} if you are unsure.
   */
  @Post("/stop-process")
  public void stopProcess() {
    minecraftProcess.destroy();
  }

  @Get("/status")
  public MinecraftReport status() {
    return MinecraftReport.builder()
        .startCommand(startCommand)
        .minecraftStatus(minecraftProcess.getStatus())
        .processStatus(minecraftProcess.getProcessStatus())
        .build();
  }

  @Get("/history")
  public String history() {
    byte[] bytes = new byte[minecraftProcess.getHistory().size()];
    for(int i = 0; i < minecraftProcess.getHistory().size(); i++) {
      bytes[i] = minecraftProcess.getHistory().get(i);
    }
    return new String(bytes);
  }
}
