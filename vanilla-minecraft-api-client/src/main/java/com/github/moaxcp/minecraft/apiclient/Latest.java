package com.github.moaxcp.minecraft.apiclient;

import io.micronaut.serde.annotation.Serdeable;
import lombok.NonNull;
import lombok.Value;

@Serdeable
@Value
public class Latest {
  @NonNull
  String release;
  @NonNull
  String snapshot;
}
