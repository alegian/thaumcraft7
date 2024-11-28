package me.alegian.thavma.impl.client.extension;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

import javax.annotation.Nonnull;

public class BEWLRItemExtensionFactory {
  public static IClientItemExtensions create(BlockEntityWithoutLevelRenderer bewlr) {
    return new IClientItemExtensions() {
      @Override
      @Nonnull
      public BlockEntityWithoutLevelRenderer getCustomRenderer() {
        return bewlr;
      }
    };
  }
}
