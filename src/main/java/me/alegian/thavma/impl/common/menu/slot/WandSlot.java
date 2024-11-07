package me.alegian.thavma.impl.common.menu.slot;

import me.alegian.thavma.impl.common.item.WandItem;
import me.alegian.thavma.impl.common.menu.Menu;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;

public class WandSlot extends T7Slot {
  public WandSlot(Container container, int id, Menu menu) {
    super(container, id, menu, 18);
  }

  @Override
  public boolean mayPlace(ItemStack pStack) {
    return pStack.getItem() instanceof WandItem;
  }
}
