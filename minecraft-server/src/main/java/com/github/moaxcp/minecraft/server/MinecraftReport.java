package com.github.moaxcp.minecraft.server;

import com.github.moaxcp.minecraft.server.cli.StartCommand;
import com.github.moaxcp.pty.Status;
import lombok.Builder;
import lombok.Value;

import java.util.List;
import java.util.Optional;

@Value
@Builder
public class MinecraftReport {
  StartCommand startCommand;
  MinecraftStatus minecraftStatus;
  Optional<Status> processStatus;
  public List<String> getCommand() {
    return startCommand.toCommand();
  }
}
