package com.github.moaxcp.minecraft.process;

import com.github.moaxcp.minecraft.server.cli.StartCommand;
import com.github.moaxcp.pty.Status;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ProcessStatus {
  StartCommand startCommand;
  Status status;
}
