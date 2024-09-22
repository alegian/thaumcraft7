package me.alegian.thaumcraft7.impl.init.registries.deferred.util;

import me.alegian.thaumcraft7.impl.common.wand.WandCoreMaterial;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.registries.DeferredHolder;

public class DeferredWandCoreMaterial<T extends WandCoreMaterial> extends DeferredHolder<WandCoreMaterial, T> {
  protected DeferredWandCoreMaterial(ResourceKey<WandCoreMaterial> key) {
    super(key);
  }

  public static <T extends WandCoreMaterial> DeferredWandCoreMaterial<T> createMaterial(ResourceKey<WandCoreMaterial> key) {
    return new DeferredWandCoreMaterial<>(key);
  }
}
