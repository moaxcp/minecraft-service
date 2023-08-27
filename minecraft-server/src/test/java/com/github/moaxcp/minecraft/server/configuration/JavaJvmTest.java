package com.github.moaxcp.minecraft.server.configuration;

import io.micronaut.serde.ObjectMapper;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.io.IOException;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

@MicronautTest
public class JavaJvmTest {
    @Test
    void serialization(ObjectMapper mapper) throws IOException, JSONException {
        var value = JavaJvm.builder()
                .command(Path.of("java"))
                .home(Path.of("/java"))
                .version("1.17")
                .build();
        var json = mapper.writeValueAsString(value);
        JSONAssert.assertEquals("""
                {
                    "command": "java",
                    "home": "/java",
                    "version": "1.17"
                }
                """, json, JSONCompareMode.STRICT);
    }
    @Test
    void deserialize(ObjectMapper mapper) throws IOException {
        var json = """
                {
                    "command": "java",
                    "home": "/java",
                    "version": "1.17"
                }
                """;
        var expected = JavaJvm.builder()
                .command(Path.of("java"))
                .home(Path.of("/java"))
                .version("1.17")
                .build();
        assertThat(mapper.readValue(json, JavaJvm.class)).isEqualTo(expected);
    }
}
