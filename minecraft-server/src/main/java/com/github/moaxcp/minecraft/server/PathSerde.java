package com.github.moaxcp.minecraft.server;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.core.type.Argument;
import io.micronaut.serde.Decoder;
import io.micronaut.serde.Encoder;
import io.micronaut.serde.Serde;
import jakarta.inject.Singleton;

import java.io.IOException;
import java.nio.file.Path;

@Singleton
public class PathSerde implements Serde<Path> {

    @Override
    public @Nullable Path deserialize(@NonNull Decoder decoder, @NonNull DecoderContext context, @NonNull Argument<? super Path> type) throws IOException {
        return Path.of(decoder.decodeString());
    }

    @Override
    public void serialize(@NonNull Encoder encoder, @NonNull EncoderContext context, @NonNull Argument<? extends Path> type, @NonNull Path value) throws IOException {
        encoder.encodeString(value.toString());
    }
}
