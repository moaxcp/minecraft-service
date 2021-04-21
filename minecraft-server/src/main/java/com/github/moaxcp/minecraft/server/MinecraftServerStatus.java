package com.github.moaxcp.minecraft.server;

import com.github.moaxcp.minecraft.server.cli.StartCommand;
import com.github.moaxcp.pty.Status;
import lombok.Builder;
import lombok.Value;

import java.util.Optional;

@Value
@Builder
public class MinecraftServerStatus {
  MinecraftConfiguration runningConfiguration;
  MinecraftConfiguration selectedConfiguration;
  StartCommand startCommand;
  MinecraftProcessStatus minecraftProcessStatus;
  Optional<Status> processStatus;
}
