package me.alegian.thaumcraft7.impl.common.menu.slot;

import me.alegian.thaumcraft7.impl.common.menu.Menu;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.ResultSlot;

/**
 * A Sized ResultSlot
 */
public class T7ResultSlot extends ResultSlot implements Sized {
  private final int size;

  public T7ResultSlot(Menu menu, CraftingContainer craftSlots, Container container, int id, int size) {
    super(menu.getPlayer(), craftSlots, container, id, menu.getSlotPose().getX(), menu.getSlotPose().getY());
    this.size = size;
  }

  @Override
  public int getSize() {
    return size;
  }
}
