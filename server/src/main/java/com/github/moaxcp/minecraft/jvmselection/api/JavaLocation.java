package com.github.moaxcp.minecraft.jvmselection.api;

import lombok.Data;

@Data
public class JavaLocation {
  private String javaHome;

  public String getCommand() {
    return javaHome + "/bin/java";
  }
}
