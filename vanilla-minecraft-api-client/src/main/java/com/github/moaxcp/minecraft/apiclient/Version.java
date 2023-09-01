package com.github.moaxcp.minecraft.apiclient;

import io.micronaut.serde.annotation.Serdeable;
import java.net.URL;
import java.time.ZonedDateTime;
import lombok.NonNull;
import lombok.Value;

@Serdeable
@Value
public class Version {
  public enum Type {
    RELEASE,
    SNAPSHOT,
    old_beta,
    old_alpha
  }
  @NonNull
  String id;
  @NonNull
  Type type;
  @NonNull
  URL url;
  @NonNull
  ZonedDateTime time;
  @NonNull
  ZonedDateTime releaseTime;
}
