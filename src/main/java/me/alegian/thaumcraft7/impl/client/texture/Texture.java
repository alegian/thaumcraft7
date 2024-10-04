package me.alegian.thaumcraft7.impl.client.texture;

import me.alegian.thaumcraft7.impl.Thaumcraft;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

/**
 * Width and height refer to the useful part of the texture, not its entirety.
 * In practice many textures (like inventories) have huge empty spaces we don't care about;
 */
@OnlyIn(Dist.CLIENT)
public record Texture(ResourceLocation location, int width, int height) {
  public Texture(String path, int width, int height) {
    this(Thaumcraft.id("textures/" + path + ".png"), width, height);
  }
}
