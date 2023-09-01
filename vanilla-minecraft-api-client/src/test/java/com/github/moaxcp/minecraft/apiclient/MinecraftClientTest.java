package com.github.moaxcp.minecraft.apiclient;

import io.micronaut.serde.ObjectMapper;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.Test;

@MicronautTest
public class MinecraftClientTest {

  private final MockWebServer server = new MockWebServer();

  @Test
  void getManifest(ObjectMapper mapper) throws IOException, InterruptedException {
    server.enqueue(new MockResponse().setBody(""));
    var client = new MinecraftClient(HttpClient.newBuilder().build(), mapper, URI.create("http://localhost:" + server.getPort() + "/mc/game/version_manifest.json"));
    client.getVersionManifest();
  }
}
