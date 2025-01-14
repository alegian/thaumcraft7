package me.alegian.thavma.impl.common.item

import io.wispforest.accessories.api.events.AccessoryChangeCallback
import io.wispforest.accessories.api.events.SlotStateChange
import me.alegian.thavma.impl.init.registries.deferred.T7Items.DAWN_CHARM
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.item.Item

class DawnCharmItem : Item(Properties().stacksTo(1)) {
    init {
        AccessoryChangeCallback.EVENT.register(AccessoryChangeCallback { _, currentStack, reference, stateChange ->
            if (
                stateChange == SlotStateChange.REPLACEMENT &&
                currentStack.item == DAWN_CHARM.get()
            )
                reference.entity().removeEffect(MobEffects.DARKNESS)
        })
    }
}
