package me.alegian.thaumcraft7.api.capability;

import me.alegian.thaumcraft7.impl.Thaumcraft;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.ItemCapability;

public final class T7Capabilities {
  public static final class VisStorage {
    public static final ItemCapability<IVisStorage, Void> ITEM = ItemCapability.createVoid(
        ResourceLocation.fromNamespaceAndPath(Thaumcraft.MODID, "vis_storage"),
        IVisStorage.class
    );
  }

  public static final class AspectContainer {
    public static final BlockCapability<IAspectContainer, Void> BLOCK = BlockCapability.createVoid(
        ResourceLocation.fromNamespaceAndPath(Thaumcraft.MODID, "aspect_container"),
        IAspectContainer.class
    );
  }

  public static final ItemCapability<Boolean, Void> REVEALING = ItemCapability.createVoid(
      ResourceLocation.fromNamespaceAndPath(Thaumcraft.MODID, "goggle"),
      Boolean.class
  );
}
