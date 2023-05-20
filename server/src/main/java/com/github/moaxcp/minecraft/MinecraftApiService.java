package com.github.moaxcp.minecraft;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.moaxcp.minecraft.apiclient.MinecraftClient;
import com.github.moaxcp.minecraft.apiclient.VersionManifest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MinecraftApiService {
  private final ObjectMapper mapper;
  private final MinecraftClient client;
  private final Path downloads;
  private final Duration maxAge;

  private VersionManifest versionManifest;

  public VersionManifest getVersionManifest() throws IOException, InterruptedException {
    Path location = downloads.resolve(Path.of("version-manifest.json"));
    if (Files.exists(location)) {
      Duration age = Duration.between(Files.getLastModifiedTime(location).toInstant(), Instant.now());
      if (age.compareTo(maxAge) >= 0) {
        versionManifest = client.getVersionManifest();
        saveFile(location, versionManifest);
      }
    } else {
      versionManifest = client.getVersionManifest();
      saveFile(location, versionManifest);
    }
    return versionManifest;
  }

  private void saveFile(Path location, Object object) throws IOException {
    byte[] bytes = mapper.writeValueAsBytes(object);
    Files.write(location, bytes);
  }
}
