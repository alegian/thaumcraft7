package me.alegian.thaumcraft7.impl.common.menu.slot;

import me.alegian.thaumcraft7.impl.common.item.WandItem;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class WandSlot extends Slot {
  public WandSlot(Container pContainer, int pSlot, int pX, int pY) {
    super(pContainer, pSlot, pX, pY);
  }

  public boolean mayPlace(ItemStack pStack) {
    return pStack.getItem() instanceof WandItem;
  }
}
