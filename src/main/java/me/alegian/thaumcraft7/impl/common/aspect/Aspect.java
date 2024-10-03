package me.alegian.thaumcraft7.impl.common.aspect;

import me.alegian.thaumcraft7.impl.init.registries.deferred.Aspects;

import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

public class Aspect {
  String id;
  int color;
  List<Supplier<Aspect>> components;

  public Aspect(String id, int color, List<Supplier<Aspect>> components) {
    this.id = id;
    this.color = color;
    this.components = components;
  }

  public boolean isPrimal() {
    return components == null;
  }

  public String getId() {
    return id;
  }

  public int getColor() {
    return color;
  }

  public int[] getColorRGB() {
    int r = (color & 0xFF0000) >> 16;
    int g = (color & 0xFF00) >> 8;
    int b = (color & 0xFF);

    return new int[]{r, g, b};
  }

  public List<Supplier<Aspect>> getComponents() {
    return components;
  }

  public static Aspect getRandomAspect() {
    var registeredAspects = Aspects.REGISTRAR.getEntries();
    return registeredAspects.stream().skip(new Random().nextInt(registeredAspects.size())).findFirst().get().get();
  }
}
