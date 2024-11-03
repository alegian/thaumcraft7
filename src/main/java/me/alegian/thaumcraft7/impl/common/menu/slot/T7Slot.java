package me.alegian.thaumcraft7.impl.common.menu.slot;

import me.alegian.thaumcraft7.impl.common.menu.Menu;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;

/**
 * A Slot that implements Sized, and has a back-reference to a Menu
 */
public class T7Slot extends Slot implements Sized {
  int size;

  public T7Slot(Container container, int id, Menu menu, int size) {
    super(container, id, menu.getSlotPose().getX(), menu.getSlotPose().getY());
    this.size = size;
  }

  @Override
  public int getSize() {
    return this.size;
  }
}
