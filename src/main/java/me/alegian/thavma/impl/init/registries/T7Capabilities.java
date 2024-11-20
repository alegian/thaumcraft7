package me.alegian.thavma.impl.init.registries;

import me.alegian.thavma.impl.Thavma;
import me.alegian.thavma.impl.common.data.capability.IAspectContainer;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.ItemCapability;

public final class T7Capabilities {
  public static final class AspectContainer {
    public static final BlockCapability<IAspectContainer, Void> BLOCK = BlockCapability.createVoid(
        Thavma.rl("aspect_container"),
        IAspectContainer.class
    );

    public static final ItemCapability<IAspectContainer, Void> ITEM = ItemCapability.createVoid(
        Thavma.rl("aspect_container"),
        IAspectContainer.class
    );
  }
}
