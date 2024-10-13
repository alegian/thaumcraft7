package me.alegian.thaumcraft7.impl.common.menu.slot;

import me.alegian.thaumcraft7.impl.common.menu.Menu;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;

public class T7Slot extends Slot {
  int size;

  public T7Slot(Container container, int id, Menu menu, int size) {
    super(container, id, menu.getSlotPose().getX(), menu.getSlotPose().getY());
    this.size = size;
  }

  public int getSize() {
    return size;
  }
}
