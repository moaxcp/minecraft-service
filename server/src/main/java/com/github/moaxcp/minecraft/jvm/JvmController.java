package com.github.moaxcp.minecraft.jvm;

import com.github.moaxcp.minecraft.configuration.JavaJvm;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.util.Map;

@Secured(SecurityRule.IS_AUTHENTICATED)
@Controller("/jvm")
@AllArgsConstructor
public class JvmController {

  @NonNull
  private JvmService jvmService;

  @Get("/")
  public Map<Integer, JavaJvm> getJvms() {
    return jvmService.getJvms();
  }
}
