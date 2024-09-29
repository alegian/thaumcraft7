package me.alegian.thaumcraft7.impl.init.registries.deferred.util;

import me.alegian.thaumcraft7.impl.common.aspect.Aspect;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.registries.DeferredHolder;

public class DeferredAspect<T extends Aspect> extends DeferredHolder<Aspect, T> {
  protected DeferredAspect(ResourceKey<Aspect> key) {
    super(key);
  }

  public static <T extends Aspect> DeferredAspect<T> createAspect(ResourceKey<Aspect> key) {
    return new DeferredAspect<>(key);
  }
}
