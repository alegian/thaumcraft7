package me.alegian.thaumcraft7.impl.init.registries;

import me.alegian.thaumcraft7.impl.Thaumcraft;
import me.alegian.thaumcraft7.impl.common.data.capability.IAspectContainer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Unit;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.ItemCapability;

public final class T7Capabilities {
  public static final class AspectContainer {
    public static final BlockCapability<IAspectContainer, Void> BLOCK = BlockCapability.createVoid(
        Thaumcraft.id("aspect_container"),
        IAspectContainer.class
    );

    public static final ItemCapability<IAspectContainer, Void> ITEM = ItemCapability.createVoid(
        Thaumcraft.id("aspect_container"),
        IAspectContainer.class
    );
  }

  public static final ItemCapability<Unit, Void> REVEALING = ItemCapability.createVoid(
      Thaumcraft.id("goggle"),
      Unit.class
  );
}
