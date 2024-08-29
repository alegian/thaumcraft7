package me.alegian.thaumcraft7.api.aspect;

public record AspectStack(Aspect aspect, int amount) {
  public static AspectStack of(Aspect aspect, int amount) {
    return new AspectStack(aspect, amount);
  }
}
