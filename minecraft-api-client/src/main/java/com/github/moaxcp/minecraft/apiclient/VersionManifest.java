package com.github.moaxcp.minecraft.apiclient;

import java.util.List;
import lombok.Value;

@Value
public class VersionManifest {
  String latestRelease;
  String latestSnapshot;
  List<Version> versions;
}
