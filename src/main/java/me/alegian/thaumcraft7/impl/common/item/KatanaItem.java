package me.alegian.thaumcraft7.impl.common.item;

import me.alegian.thaumcraft7.impl.Thaumcraft;
import me.alegian.thaumcraft7.impl.init.registries.T7Tiers;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.function.Consumer;

public class KatanaItem extends SwordItem implements GeoItem {
  private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

  public KatanaItem() {
    super(T7Tiers.ARCANUM_TIER,
        new Item.Properties().attributes(
            SwordItem.createAttributes(
                T7Tiers.ARCANUM_TIER,
                3, -2.4f
            )
        ));
  }

  @Override
  public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

  }

  @Override
  public AnimatableInstanceCache getAnimatableInstanceCache() {
    return cache;
  }

  @Override
  public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
    consumer.accept(new GeoRenderProvider() {
      private GeoItemRenderer<?> renderer;

      @Override
      public BlockEntityWithoutLevelRenderer getGeoItemRenderer() {
        if (this.renderer == null)
          this.renderer = new GeoItemRenderer<>(new DefaultedItemGeoModel<KatanaItem>(ResourceLocation.fromNamespaceAndPath(Thaumcraft.MODID, "arcanum_katana")));

        return this.renderer;
      }
    });
  }
}
