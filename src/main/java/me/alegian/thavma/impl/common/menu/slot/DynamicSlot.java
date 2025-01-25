package me.alegian.thavma.impl.common.menu.slot;

import me.alegian.thavma.impl.common.menu.Menu;

/**
 * Used for adding size to slots, allowing them to be dynamically positioned
 */
public interface DynamicSlot<T extends Menu> {
  int getX();

  int getY();

  void setX(int x);

  void setY(int y);

  int getSize();

  T getMenu();
}
