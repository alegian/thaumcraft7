package me.alegian.thavma.impl.common.menu.container;

import me.alegian.thavma.impl.common.menu.slot.SlotRange;
import net.minecraft.world.Container;

/**
 * A Container that self-adds slots to the corresponding Menu,
 * and keeps track of its indexes, to help with quick-moves.
 */
public interface T7Container extends Container {
  void addSlots();

  SlotRange getRange();
}
