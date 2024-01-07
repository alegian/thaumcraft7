package me.alegian.thaumcraft7.capability;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.capabilities.ItemCapability;

public final class ThaumcraftCapabilities {
    public static final class VisStorage {
        public static final ItemCapability<IVisStorage, Void> ITEM = ItemCapability.createVoid(
                new ResourceLocation("thaumcraft7", "vis_storage"),
                IVisStorage.class
        );
    }
}
