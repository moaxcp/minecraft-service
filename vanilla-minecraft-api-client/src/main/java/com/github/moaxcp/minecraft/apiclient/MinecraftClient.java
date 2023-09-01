package com.github.moaxcp.minecraft.apiclient;

import io.micronaut.serde.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class MinecraftClient {

  @NonNull
  private final HttpClient client;
  @NonNull
  private final ObjectMapper mapper;
  @NonNull
  private final URI versionManifestLocation;

  public VersionManifest getVersionManifest() throws IOException, InterruptedException {
    try (var in = client.send(HttpRequest.newBuilder().GET().uri(versionManifestLocation).build(), BodyHandlers.ofInputStream()).body()) {
      return mapper.readValue(in, VersionManifest.class);
    }
  }
}
