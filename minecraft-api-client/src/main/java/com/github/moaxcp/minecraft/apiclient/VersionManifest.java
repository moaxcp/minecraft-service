package com.github.moaxcp.minecraft.apiclient;

import java.util.List;
import lombok.NonNull;
import lombok.Value;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
@Value
public class VersionManifest {
  @NonNull
  Latest latest;
  @NonNull
  List<Version> versions;
}
