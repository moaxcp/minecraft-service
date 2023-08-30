package com.github.moaxcp.minecraft.configuration;

import io.micronaut.context.annotation.Property;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

@MicronautTest
@Property(name = "base-directory", value = "#{ T(java.nio.file.Files).createTempDirectory('tmpDirPrefix') }")
public class PathTest {
    @Test
    @Disabled
    void test(@Property(name = "base-directory") Path baseDirectory) {
        assertThat(baseDirectory).hasFileName("tmpDirPrefix");
    }
}
