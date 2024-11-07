package me.alegian.thavma.impl.client.texture.atlas;

import me.alegian.thavma.impl.Thavma;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.TextureAtlasHolder;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class AspectAtlas extends TextureAtlasHolder {
  public static final String KEY = "aspects";
  public static final ResourceLocation LOCATION = Thavma.id("atlas/" + KEY);
  public static final AspectAtlas INSTANCE = new AspectAtlas(Minecraft.getInstance().getTextureManager());

  public AspectAtlas(TextureManager pTextureManager) {
    super(pTextureManager, LOCATION, Thavma.id(KEY));
  }

  @Override
  public @NotNull TextureAtlasSprite getSprite(@NotNull ResourceLocation pLocation) {
    return super.getSprite(pLocation);
  }

  public static TextureAtlasSprite sprite(ResourceLocation pLocation) {
    return INSTANCE.getSprite(pLocation);
  }
}