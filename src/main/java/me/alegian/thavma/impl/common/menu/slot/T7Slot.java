package me.alegian.thavma.impl.common.menu.slot;

import me.alegian.thavma.impl.common.menu.Menu;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;

/**
 * A Slot that implements Sized, and has a back-reference to a Menu
 */
public class T7Slot<T extends Menu> extends Slot implements Sized {
  private final int size;
  private final T menu;

  public T7Slot(Container container, int id, T menu, int size) {
    super(container, id, menu.getSlotPose().getX(), menu.getSlotPose().getY());
    this.size = size;
    this.menu = menu;
  }

  public T getMenu() {
    return this.menu;
  }

  @Override
  public int getSize() {
    return this.size;
  }
}
