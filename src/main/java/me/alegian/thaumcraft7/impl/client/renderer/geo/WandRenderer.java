package me.alegian.thaumcraft7.impl.client.renderer.geo;

import me.alegian.thaumcraft7.impl.Thaumcraft;
import me.alegian.thaumcraft7.impl.common.item.WandItem;
import me.alegian.thaumcraft7.impl.common.wand.WandCoreMaterial;
import me.alegian.thaumcraft7.impl.common.wand.WandHandleMaterial;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.DeferredHolder;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.specialty.DynamicGeoItemRenderer;

import javax.annotation.Nullable;

public class WandRenderer extends DynamicGeoItemRenderer<WandItem> {
  private static final ResourceLocation CIRCLE = texture(ResourceLocation.fromNamespaceAndPath(Thaumcraft.MODID, "wand_circle"));
  private final ResourceLocation handleLocation;
  private final ResourceLocation coreLocation;

  public WandRenderer(DeferredHolder<WandHandleMaterial, WandHandleMaterial> handleMaterial, DeferredHolder<WandCoreMaterial, WandCoreMaterial> coreMaterial) {
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

  private static ResourceLocation handleTexture(ResourceLocation registeredLocation) {
    return texture(registeredLocation, "wand_handle_");
  }

  private static ResourceLocation coreTexture(ResourceLocation registeredLocation) {
    return texture(registeredLocation, "wand_stick_");
  }

  private static ResourceLocation texture(ResourceLocation registeredLocation) {
    return texture(registeredLocation, "");
  }

  private static ResourceLocation texture(ResourceLocation registeredLocation, String prefix) {
    return ResourceLocation.fromNamespaceAndPath(registeredLocation.getNamespace(), "textures/item/" + prefix + registeredLocation.getPath() + ".png");
  }
}
