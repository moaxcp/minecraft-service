package com.github.moaxcp.minecraft.server.configuration;

import io.micronaut.serde.ObjectMapper;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.micronaut.validation.validator.Validator;
import org.json.JSONException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static com.github.moaxcp.minecraft.server.configuration.Memory.memory;
import static org.assertj.core.api.Assertions.assertThat;

@MicronautTest
public class MemoryTest {

    @Test
    void validation(Validator validator) {
        var value = memory(-1, null);
        var violations = validator.validate(value);
        assertThat(violations).anySatisfy(v -> assertThat(v.getMessage()).isEqualTo("must be greater than or equal to 1"))
                .anySatisfy(v -> assertThat(v.getMessage()).isEqualTo("must not be null"));
    }

    @Test
    void serialization(ObjectMapper mapper) throws IOException, JSONException {
        var value = memory(1, MemoryUnit.G);
        var json = mapper.writeValueAsString(value);
        assertThat(json).isEqualTo("\"1g\"");
    }

    @Test
    void deserialize(ObjectMapper mapper) throws IOException {
        var json = "\"1g\"";
        assertThat(mapper.readValue(json, Memory.class)).isEqualTo(memory(1, MemoryUnit.G));
    }
}
