package com.github.moaxcp.minecraft.process;


import com.github.moaxcp.minecraft.server.MinecraftProcess;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.websocket.WebSocketBroadcaster;
import io.micronaut.websocket.WebSocketSession;
import io.micronaut.websocket.annotation.OnClose;
import io.micronaut.websocket.annotation.OnMessage;
import io.micronaut.websocket.annotation.OnOpen;
import io.micronaut.websocket.annotation.ServerWebSocket;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Secured(SecurityRule.IS_AUTHENTICATED)
@ServerWebSocket("/console")
@RequiredArgsConstructor
public class ConsoleWebsocket {
  @NonNull
  private final MinecraftProcess minecraftProcess;
  @NonNull
  private final WebSocketBroadcaster broadcaster;

  @OnOpen
  public void onOpen(WebSocketSession session) throws IOException {
    session.sendAsync(minecraftProcess.getHistory());
  }

  @OnMessage
  public void onMessage(String message, WebSocketSession session) {
    if(minecraftProcess.isRunning()) {
      minecraftProcess.input(message.getBytes(StandardCharsets.UTF_8));
    }
  }

  @OnClose
  public void onClose(WebSocketSession session) {

  }
}
