package me.alegian.thaumcraft7.impl.common.menu.slot;

import me.alegian.thaumcraft7.impl.common.item.WandItem;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;

public class WandSlot extends T7Slot {


  public WandSlot(Container container, int id, SlotPose pose) {
    super(container, id, pose, 18);
  }

  public boolean mayPlace(ItemStack pStack) {
    return pStack.getItem() instanceof WandItem;
  }
}
