package me.alegian.thavma.impl.common.item;

import io.wispforest.accessories.api.components.AccessoriesDataComponents;
import io.wispforest.accessories.api.components.AccessoryItemAttributeModifiers;
import me.alegian.thavma.impl.init.registries.T7AttributeModifiers;
import me.alegian.thavma.impl.init.registries.deferred.T7Attributes;
import net.minecraft.world.item.Item;

public class GogglesAccessoryItem extends Item {
  public GogglesAccessoryItem(Properties props) {
    super(new Item.Properties()
        .stacksTo(1)
        .component(
            AccessoriesDataComponents.ATTRIBUTES,
            AccessoryItemAttributeModifiers.builder().addForAny(
                T7Attributes.INSTANCE.getREVEALING(),
                T7AttributeModifiers.Revealing.GOGGLES_ACCESSORY,
                false
            ).build())
    );
  }
}
