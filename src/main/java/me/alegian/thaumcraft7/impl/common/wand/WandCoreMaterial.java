package me.alegian.thaumcraft7.impl.common.wand;

import me.alegian.thaumcraft7.impl.init.registries.T7Registries;
import net.minecraft.resources.ResourceLocation;

public record WandCoreMaterial(boolean registerCombinations, int capacity) {
  public WandCoreMaterial(int capacity) {
    this(true, capacity);
  }

  public String getRegisteredName() {
    return getRegisteredLocation().getPath();
  }

  public ResourceLocation getRegisteredLocation() {
    return T7Registries.WAND_CORE.getKey(this);
  }
}
