package me.alegian.thaumcraft7.impl.client.renderer.geo;

import me.alegian.thaumcraft7.impl.Thaumcraft;
import me.alegian.thaumcraft7.impl.common.item.WandItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.specialty.DynamicGeoItemRenderer;

import javax.annotation.Nullable;

public class WandRenderer extends DynamicGeoItemRenderer<WandItem> {
  private static final ResourceLocation CIRCLE = texture("wand_circle");
  private final ResourceLocation handleLocation;
  private final ResourceLocation coreLocation;

  public WandRenderer(WandItem.HandleMaterial handleMaterial, WandItem.CoreMaterial coreMaterial) {
    super(new DefaultedItemGeoModel<>(ResourceLocation.fromNamespaceAndPath(Thaumcraft.MODID, "wand")));
    this.handleLocation = handleTexture(handleMaterial.getId());
    this.coreLocation = coreTexture(coreMaterial.getId());
  }

  @Nullable
  @Override
  protected ResourceLocation getTextureOverrideForBone(GeoBone bone, WandItem animatable, float partialTick) {
    return switch (bone.getName()) {
      case "handle" -> handleLocation;
      case "stick" -> coreLocation;
      default -> CIRCLE;
    };
  }

  private static ResourceLocation handleTexture(String name) {
    return texture("wand_handle_" + name);
  }

  private static ResourceLocation coreTexture(String name) {
    return texture("wand_stick_" + name);
  }

  private static ResourceLocation texture(String name) {
    return ResourceLocation.fromNamespaceAndPath(Thaumcraft.MODID, "textures/item/" + name + ".png");
  }
}
