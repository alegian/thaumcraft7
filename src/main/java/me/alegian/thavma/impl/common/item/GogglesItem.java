package me.alegian.thavma.impl.common.item;

import io.wispforest.accessories.api.components.AccessoriesDataComponents;
import io.wispforest.accessories.api.components.AccessoryItemAttributeModifiers;
import me.alegian.thavma.impl.Thavma;
import me.alegian.thavma.impl.init.registries.T7AttributeModifiers;
import me.alegian.thavma.impl.init.registries.deferred.T7ArmorMaterials;
import me.alegian.thavma.impl.init.registries.deferred.T7Attributes;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.function.Consumer;

public class GogglesItem extends ArmorItem implements GeoItem {
  private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

  public GogglesItem(Properties props) {
    super(T7ArmorMaterials.GOGGLES, ArmorItem.Type.HELMET, props
        .durability(Type.HELMET.getDurability(15))
        .component(
            AccessoriesDataComponents.ATTRIBUTES,
            AccessoryItemAttributeModifiers.builder().addForAny(
                T7Attributes.REVEALING,
                T7AttributeModifiers.Revealing.GOGGLES_ACCESSORY,
                false
            ).build())
        .attributes(
            ItemAttributeModifiers.builder().add(
                T7Attributes.REVEALING,
                T7AttributeModifiers.Revealing.GOGGLES,
                EquipmentSlotGroup.HEAD
            ).build()
        ));
  }

  @Override
  public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

  }

  @Override
  public AnimatableInstanceCache getAnimatableInstanceCache() {
    return this.cache;
  }

  @Override
  public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
    consumer.accept(new GeoRenderProvider() {
      private GeoArmorRenderer<?> renderer;

      @Override
      public <T extends LivingEntity> HumanoidModel<?> getGeoArmorRenderer(@Nullable T livingEntity, ItemStack itemStack, @Nullable EquipmentSlot equipmentSlot, @Nullable HumanoidModel<T> original) {
        if (this.renderer == null)
          this.renderer = new GeoArmorRenderer<>(new DefaultedItemGeoModel<GogglesItem>(Thavma.id("goggles_armor")));

        return this.renderer;
      }
    });
  }
}
