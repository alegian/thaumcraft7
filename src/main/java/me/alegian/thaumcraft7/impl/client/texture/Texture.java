package me.alegian.thaumcraft7.impl.client.texture;

import me.alegian.thaumcraft7.impl.Thaumcraft;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public record Texture(ResourceLocation location, int width, int height) {
  public Texture(String path, int width, int height) {
    this(Thaumcraft.id("textures/" + path + ".png"), width, height);
  }
}
