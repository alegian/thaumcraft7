package me.alegian.thaumcraft7.impl.common.wand;


import me.alegian.thaumcraft7.impl.init.registries.T7Registries;
import net.minecraft.resources.ResourceLocation;

public record WandHandleMaterial(boolean registerCombinations) {
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
