package me.alegian.thaumcraft7.impl.init.registries.deferred.util;

import me.alegian.thaumcraft7.impl.common.wand.WandHandleMaterial;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.registries.DeferredHolder;

public class DeferredWandHandleMaterial<T extends WandHandleMaterial> extends DeferredHolder<WandHandleMaterial, T> {
  protected DeferredWandHandleMaterial(ResourceKey<WandHandleMaterial> key) {
    super(key);
  }

  public static <T extends WandHandleMaterial> DeferredWandHandleMaterial<T> createMaterial(ResourceKey<WandHandleMaterial> key) {
    return new DeferredWandHandleMaterial<>(key);
  }
}
