package me.alegian.thavma.impl.common.item

import io.wispforest.accessories.api.components.AccessoriesDataComponents
import io.wispforest.accessories.api.components.AccessoryItemAttributeModifiers
import me.alegian.thavma.impl.init.registries.T7AttributeModifiers.Revealing.GOGGLES_ACCESSORY
import me.alegian.thavma.impl.init.registries.deferred.T7Attributes.REVEALING
import net.minecraft.world.item.Item

class GogglesAccessoryItem : Item(
    Properties()
        .stacksTo(1)
        .component(
            AccessoriesDataComponents.ATTRIBUTES,
            AccessoryItemAttributeModifiers.builder().addForAny(
                REVEALING,
                GOGGLES_ACCESSORY,
                false
            ).build()
        )
)
