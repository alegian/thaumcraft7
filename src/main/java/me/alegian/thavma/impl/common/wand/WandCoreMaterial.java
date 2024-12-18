package me.alegian.thavma.impl.common.wand;

import me.alegian.thavma.impl.init.registries.T7Registries;
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
    return this.getRegisteredLocation().getPath();
  }

  public ResourceLocation getRegisteredLocation() {
    return T7Registries.INSTANCE.getWAND_CORE().getKey(this);
  }
}
