package me.alegian.thaumcraft7.impl.common.menu.container;

import me.alegian.thaumcraft7.impl.common.menu.slot.SlotRange;
import net.minecraft.world.Container;

/**
 * A Container that self-adds slots to the corresponding AbstractContainerMenu,
 * and keeps track of its indexes, to help with quick-moves.
 * Mainly used together with Menu.
 */
public interface T7Container extends Container {
  void addSlots();

  SlotRange getRange();
}
