package me.alegian.thaumcraft7.impl.client.texture;

import me.alegian.thaumcraft7.impl.Thaumcraft;
import net.minecraft.resources.ResourceLocation;

public record Texture(ResourceLocation location, int width, int height) {
  public Texture(String path, int width, int height) {
    this(ResourceLocation.fromNamespaceAndPath(Thaumcraft.MODID, "textures/" + path + ".png"), width, height);
  }
}
