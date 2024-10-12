package me.alegian.thaumcraft7.impl.common.menu.slot;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;

public class T7Slot extends Slot {
  int size;

  public T7Slot(Container container, int id, SlotPose pose, int size) {
    super(container, id, pose.getX(), pose.getY());
    this.size = size;
  }

  public int getSize() {
    return size;
  }
}
