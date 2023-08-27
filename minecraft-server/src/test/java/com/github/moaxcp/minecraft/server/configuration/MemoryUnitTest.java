package com.github.moaxcp.minecraft.server.configuration;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@MicronautTest
public class MemoryUnitTest {

    @Test
    void parse_null() {
        assertThatThrownBy(() -> MemoryUnit.parse(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("s is marked non-null but is null");
    }

    @Test
    void parse_return_null() {
        assertThat(MemoryUnit.parse("a")).isNull();
    }
}
