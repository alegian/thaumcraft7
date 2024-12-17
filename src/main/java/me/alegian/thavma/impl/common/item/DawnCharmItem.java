package me.alegian.thavma.impl.common.item;

import io.wispforest.accessories.api.events.AccessoryChangeCallback;
import io.wispforest.accessories.api.events.SlotStateChange;
import me.alegian.thavma.impl.init.registries.deferred.T7Items;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;

public class DawnCharmItem extends Item {
  public DawnCharmItem(Properties properties) {
    super(properties.stacksTo(1));

    AccessoryChangeCallback.EVENT.register((prevStack, currentStack, reference, stateChange) -> {
      if (stateChange != SlotStateChange.REPLACEMENT) return;
      if (!currentStack.getItem().equals(T7Items.INSTANCE.getDAWN_CHARM().get())) return;
      reference.entity().removeEffect(MobEffects.DARKNESS);
    });
  }
}
