package me.alegian.thavma.impl.common.menu.slot

import me.alegian.thavma.impl.common.menu.Menu
import net.minecraft.world.inventory.Slot

/**
 * Keeps track of slot index ranges. Need to call start() and end() before the
 * addition of the first slot and after the addition of the last slot
 */
open class SlotRange(protected var menu: Menu) {
  var start: Int = 0
    protected set
  var end: Int = 0
    protected set

  fun start() {
    this.start = menu.slots.size
  }

  fun end() {
    this.end = menu.slots.size - 1
  }

  fun contains(slotId: Int): Boolean {
    return slotId >= this.start && slotId <= this.end
  }

  val slots: List<Slot>
    get() = menu.slots.subList(this.start, this.end + 1)

  /**
   * Tracking single slots should be done AFTER adding them
   */
  class Single(menu: Menu) : SlotRange(menu) {
    fun track() {
      this.start = menu.slots.size - 1
      this.end = menu.slots.size - 1
    }

    val slot: Slot
      get() = menu.slots[start]
  }
}
