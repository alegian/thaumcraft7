package me.alegian.thaumcraft7.impl.client.renderer.geo;

import me.alegian.thaumcraft7.impl.Thaumcraft;
import me.alegian.thaumcraft7.impl.common.item.WandItem;
import me.alegian.thaumcraft7.impl.common.wand.WandCoreMaterial;
import me.alegian.thaumcraft7.impl.common.wand.WandHandleMaterial;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.specialty.DynamicGeoItemRenderer;

import javax.annotation.Nullable;

public class WandRenderer extends DynamicGeoItemRenderer<WandItem> {
  private final ResourceLocation handleLocation;
  private final ResourceLocation coreLocation;

  public WandRenderer(WandHandleMaterial handleMaterial, WandCoreMaterial coreMaterial) {
    super(new DefaultedItemGeoModel<>(Thaumcraft.id("wand")));
    this.handleLocation = handleTexture(handleMaterial.getRegisteredLocation());
    this.coreLocation = coreTexture(coreMaterial.getRegisteredLocation());
  }

  @Nullable
  @Override
  protected ResourceLocation getTextureOverrideForBone(GeoBone bone, WandItem animatable, float partialTick) {
    return switch (bone.getName()) {
      case "handle" -> handleLocation;
      case "stick" -> coreLocation;
      default -> null;
    };
  }

  private static ResourceLocation handleTexture(ResourceLocation registeredLocation) {
    return texture(registeredLocation, "wand_handle_");
  }

  private static ResourceLocation coreTexture(ResourceLocation registeredLocation) {
    return texture(registeredLocation, "wand_core_");
  }

  private static ResourceLocation texture(ResourceLocation registeredLocation, String prefix) {
    return registeredLocation.withPrefix("textures/item/" + prefix).withSuffix(".png");
  }
}
