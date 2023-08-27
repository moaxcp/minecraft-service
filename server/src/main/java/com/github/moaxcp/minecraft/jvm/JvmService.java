package com.github.moaxcp.minecraft.jvm;

import com.github.moaxcp.minecraft.server.configuration.JavaJvm;

import javax.inject.Singleton;
import java.util.Collections;
import java.util.Map;

import static com.github.moaxcp.minecraft.server.configuration.JavaJvm.detectCurrentJvm;

@Singleton
public class JvmService {
  private Map<Integer, JavaJvm> jvms;

  public JvmService() {
    jvms = Map.of(1, detectCurrentJvm());
  }

  public Map<Integer, JavaJvm> getJvms() {
    return Collections.unmodifiableMap(jvms);
  }

}
