package com.github.moaxcp.minecraft.process;

import com.github.moaxcp.minecraft.server.MinecraftProcess;
import com.github.moaxcp.pty.Status;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import lombok.NonNull;

import java.util.Optional;

@Controller("/minecraft-process")
public class MinecraftProcessController {
  @NonNull
  private final MinecraftProcess minecraftProcess;

  public MinecraftProcessController(@NonNull MinecraftProcess minecraftProcess) {
    this.minecraftProcess = minecraftProcess;
  }

  @Post("/start")
  public void start() {
    minecraftProcess.start();
  }

  @Get("/status")
  public ProcessStatus status() {
    return ProcessStatus.builder()
      .startCommand(minecraftProcess.getStartCommand())
      .status(minecraftProcess.getStatus().orElseGet(() -> null))
      .build();
  }
}
