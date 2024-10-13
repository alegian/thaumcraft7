package me.alegian.thaumcraft7.impl.common.wand;

import me.alegian.thaumcraft7.impl.init.registries.T7Registries;
import net.minecraft.resources.ResourceLocation;

public class WandCoreMaterial {
  public final boolean registerCombinations;
  public final int capacity;

  public WandCoreMaterial(boolean registerCombinations, int capacity) {
    this.registerCombinations = registerCombinations;
    this.capacity = capacity;
  }

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
