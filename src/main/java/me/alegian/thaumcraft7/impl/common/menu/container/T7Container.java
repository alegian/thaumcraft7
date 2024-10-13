package me.alegian.thaumcraft7.impl.common.menu.container;

import me.alegian.thaumcraft7.impl.common.menu.slot.SlotRange;
import net.minecraft.world.Container;

public interface T7Container extends Container {
  void addSlots();

  SlotRange getRange();
}
