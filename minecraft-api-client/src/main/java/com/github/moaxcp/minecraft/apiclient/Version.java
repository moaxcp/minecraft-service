package com.github.moaxcp.minecraft.apiclient;

import java.net.URL;
import java.time.ZonedDateTime;
import lombok.Data;

@Data
public class Version {
  public enum Type {
    RELEASE,
    SNAPSHOT
  }
  String id;
  Type type;
  URL url;
  ZonedDateTime time;
  ZonedDateTime releaseTime;
}
