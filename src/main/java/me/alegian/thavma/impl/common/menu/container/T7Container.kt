package me.alegian.thavma.impl.common.menu.container

import me.alegian.thavma.impl.common.menu.slot.SlotRange
import net.minecraft.world.Container

/**
 * A Container that self-adds slots to the corresponding Menu,
 * and keeps track of its indexes, to help with quick-moves.
 */
interface T7Container : Container {
  fun addSlots()

  val range: SlotRange
}
