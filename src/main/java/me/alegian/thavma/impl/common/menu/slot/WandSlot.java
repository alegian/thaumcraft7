package me.alegian.thavma.impl.common.menu.slot;

import me.alegian.thavma.impl.common.item.WandItem;
import me.alegian.thavma.impl.common.menu.Menu;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;

public class WandSlot<T extends Menu> extends T7Slot<T> {
  public WandSlot(Container container, int id, T menu) {
    super(container, id, menu);
  }

  @Override
  public boolean mayPlace(ItemStack pStack) {
    return pStack.getItem() instanceof WandItem;
  }
}
