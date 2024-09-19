package me.alegian.thaumcraft7.impl.client.renderer.geo;

import me.alegian.thaumcraft7.impl.Thaumcraft;
import me.alegian.thaumcraft7.impl.common.item.WandItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.specialty.DynamicGeoItemRenderer;

import javax.annotation.Nullable;

public class WandRenderer extends DynamicGeoItemRenderer<WandItem> {
  private static final ResourceLocation HANDLE =
      ResourceLocation.fromNamespaceAndPath(Thaumcraft.MODID, "textures/item/wand_handle_arcanum.png");
  private static final ResourceLocation CORE =
      ResourceLocation.fromNamespaceAndPath(Thaumcraft.MODID, "textures/item/wand_stick_silverwood.png");
  private static final ResourceLocation CIRCLE =
      ResourceLocation.fromNamespaceAndPath(Thaumcraft.MODID, "textures/item/wand_circle.png");

  public WandRenderer() {
    super(new DefaultedItemGeoModel<>(ResourceLocation.fromNamespaceAndPath(Thaumcraft.MODID, "wand")));
  }

  @Nullable
  @Override
  protected ResourceLocation getTextureOverrideForBone(GeoBone bone, WandItem animatable, float partialTick) {
    return switch (bone.getName()) {
      case "handle" -> HANDLE;
      case "stick" -> CORE;
      default -> CIRCLE;
    };
  }
}
