package me.alegian.thavma.impl.common.wand;


import me.alegian.thavma.impl.init.registries.T7Registries;
import net.minecraft.resources.ResourceLocation;

public class WandHandleMaterial {
  public final boolean registerCombinations;

  public WandHandleMaterial(boolean registerCombinations) {
    this.registerCombinations = registerCombinations;
  }

  public WandHandleMaterial() {
    this(true);
  }

  public String getRegisteredName() {
    return getRegisteredLocation().getPath();
  }

  public ResourceLocation getRegisteredLocation() {
    return T7Registries.WAND_HANDLE.getKey(this);
  }
}
