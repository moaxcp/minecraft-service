package com.github.moaxcp.minecraft.process.api;

import com.github.moaxcp.minecraft.server.cli.StartCommand;
import com.github.moaxcp.pty.Status;
import lombok.Builder;
import lombok.Value;

@Builder
public class MinecraftProcessStatus {
  StartCommand startCommand;
  Status status;
}
