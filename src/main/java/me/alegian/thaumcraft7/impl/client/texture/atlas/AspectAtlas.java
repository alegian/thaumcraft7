package me.alegian.thaumcraft7.impl.client.texture.atlas;

import me.alegian.thaumcraft7.impl.Thaumcraft;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.TextureAtlasHolder;
import net.minecraft.resources.ResourceLocation;

public class AspectAtlas extends TextureAtlasHolder {
  public static final String KEY = "aspects";
  public static final ResourceLocation LOCATION = ResourceLocation.fromNamespaceAndPath(Thaumcraft.MODID, "atlas/" + KEY);
  public static final AspectAtlas INSTANCE = new AspectAtlas(Minecraft.getInstance().getTextureManager());

  public AspectAtlas(TextureManager pTextureManager) {
    super(pTextureManager, LOCATION, ResourceLocation.fromNamespaceAndPath(Thaumcraft.MODID, KEY));
  }

  @Override
  public TextureAtlasSprite getSprite(ResourceLocation pLocation) {
    return super.getSprite(pLocation);
  }

  public static TextureAtlasSprite sprite(ResourceLocation pLocation) {
    return INSTANCE.getSprite(pLocation);
  }
}
