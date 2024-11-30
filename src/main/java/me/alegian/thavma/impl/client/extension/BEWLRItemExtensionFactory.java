package me.alegian.thavma.impl.client.extension;

import me.alegian.thavma.impl.client.renderer.blockentity.withoutlevel.BlockItemBEWLR;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

import javax.annotation.Nonnull;

public class BEWLRItemExtensionFactory {
  public static IClientItemExtensions create(BlockEntity be) {
    return new IClientItemExtensions() {
      @Override
      @Nonnull
      public BlockEntityWithoutLevelRenderer getCustomRenderer() {
        return new BlockItemBEWLR(be);
      }
    };
  }
}
