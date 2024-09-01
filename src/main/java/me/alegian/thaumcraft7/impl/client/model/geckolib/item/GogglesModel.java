package me.alegian.thaumcraft7.impl.client.model.geckolib.item;

import me.alegian.thaumcraft7.impl.Thaumcraft;
import me.alegian.thaumcraft7.impl.common.item.GogglesItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class GogglesModel extends GeoModel<GogglesItem> {
  private final ResourceLocation model =  ResourceLocation.fromNamespaceAndPath(Thaumcraft.MODID, "geo/goggles.geo.json");
  private final ResourceLocation texture = ResourceLocation.fromNamespaceAndPath(Thaumcraft.MODID, "textures/item/goggles_armor.png");
  private final ResourceLocation animations = ResourceLocation.fromNamespaceAndPath(Thaumcraft.MODID, "animations/goggles.animation.json");

  @Override
  public ResourceLocation getModelResource(GogglesItem animatable) {
    return model;
  }

  @Override
  public ResourceLocation getTextureResource(GogglesItem animatable) {
    return texture;
  }

  @Override
  public ResourceLocation getAnimationResource(GogglesItem animatable) {
    return animations;
  }
}
