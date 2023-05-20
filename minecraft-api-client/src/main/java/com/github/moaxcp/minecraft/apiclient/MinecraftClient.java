package com.github.moaxcp.minecraft.apiclient;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MinecraftClient {

  private final HttpClient client;
  private final ObjectMapper mapper;
  private final URI versionManifestLocation;

  public VersionManifest getVersionManifest() throws IOException, InterruptedException {
    try (var in = client.send(HttpRequest.newBuilder().GET().uri(versionManifestLocation).build(), BodyHandlers.ofInputStream()).body()) {
      return mapper.readValue(in, VersionManifest.class);
    }
  }
}
