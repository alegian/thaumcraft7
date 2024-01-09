package me.alegian.thaumcraft7.api.capabilities;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.ItemCapability;

public final class ThaumcraftCapabilities {
    public static final class VisStorage {
        public static final ItemCapability<IVisStorage, Void> ITEM = ItemCapability.createVoid(
                new ResourceLocation("thaumcraft7", "vis_storage"),
                IVisStorage.class
        );
    }

    public static final class ThaumometerScannable {
        public static final BlockCapability<IAspectContainer, Void> BLOCK = BlockCapability.createVoid(
                new ResourceLocation("thaumcraft7", "thaumometer_scannable"),
                IAspectContainer.class
        );
    }
}
