package com.github.moaxcp.minecraft.process;

import com.github.moaxcp.minecraft.server.MinecraftServerStatus;
import com.github.moaxcp.minecraft.server.MinecraftService;
import com.github.moaxcp.minecraft.server.cli.StartCommand;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import lombok.NonNull;

import java.util.Optional;

@Controller("/minecraft-process")
public class MinecraftProcessController {
  @NonNull
  private final MinecraftService minecraftService;

  public MinecraftProcessController(@NonNull MinecraftService minecraftService) {
    this.minecraftService = minecraftService;
  }

  @Get("/running-start-command")
  public Optional<StartCommand> runningStartCommand() {
    return minecraftService.getRunningStartCommand();
  }

  @Post("/start")
  public void start() {
    minecraftService.startProcess();
  }

  @Post("/stop")
  public void stop() {
    minecraftService.stopProcess();
  }

  /**
   * Calls the destroy method on the pty process for minecraft. This is supposed to gracefully shutdown the server but
   * sending "stop" in the console is preferred. Use {@link #stop()} if you are unsure.
   */
  @Post("/stop-process")
  public void stopProcess() {
    minecraftService.destroy();
  }

  @Get("/status")
  public Optional<MinecraftServerStatus> status() {
    return minecraftService.getServerStatus();
  }

  @Get("/history")
  public Optional<String> history() {
    return minecraftService.getHistory();
  }
}
