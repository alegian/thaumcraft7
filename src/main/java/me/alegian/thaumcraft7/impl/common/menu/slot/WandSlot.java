package me.alegian.thaumcraft7.impl.common.menu.slot;

import me.alegian.thaumcraft7.impl.common.item.WandItem;
import me.alegian.thaumcraft7.impl.common.menu.Menu;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;

public class WandSlot extends T7Slot {
  public WandSlot(Container container, int id, Menu menu) {
    super(container, id, menu, 18);
  }

  public boolean mayPlace(ItemStack pStack) {
    return pStack.getItem() instanceof WandItem;
  }
}
