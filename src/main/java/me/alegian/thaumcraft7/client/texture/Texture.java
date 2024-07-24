package me.alegian.thaumcraft7.client.texture;

import me.alegian.thaumcraft7.Thaumcraft;
import net.minecraft.resources.ResourceLocation;

public record Texture(ResourceLocation location, int width, int height) {
  public Texture(String path, int width, int height) {
    this(ResourceLocation.fromNamespaceAndPath(Thaumcraft.MODID, "textures/" + path + ".png"), width, height);
  }
}
