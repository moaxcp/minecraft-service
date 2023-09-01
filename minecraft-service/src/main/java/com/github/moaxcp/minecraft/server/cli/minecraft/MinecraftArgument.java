package com.github.moaxcp.minecraft.server.cli.minecraft;

import com.github.moaxcp.minecraft.server.cli.AbstractArgument;
import lombok.NonNull;

import java.nio.file.Path;

public abstract class MinecraftArgument extends AbstractArgument {

  public static BonusChest bonusChest() {
    return new BonusChest();
  }

  public static Demo demo() {
    return new Demo();
  }

  public static EraseCache eraseCache() {
    return new EraseCache();
  }

  public static Nogui nogui() {
    return new Nogui();
  }

  public static Universe universe(Path universe) {
    return new Universe(universe);
  }

  public static World world(String world) {
    return new World(world);
  }

  public MinecraftArgument(@NonNull String arg) {
    super(arg);
  }

  public MinecraftArgument(@NonNull String key, @NonNull String value) {
    super(key, value);
  }
}
